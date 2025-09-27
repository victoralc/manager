package app.domain.orders;

import app.domain.customers.CustomerResource;
import app.domain.customers.model.Customer;
import app.domain.payments.PaymentService;
import app.domain.products.Product;
import app.domain.products.ProductRepository;
import app.domain.users.UserRepository;
import app.service.EmailService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
public class OrderResource {
    @Inject
    OrderRepository orderRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    PaymentService paymentService;

    @Inject
    OrderService orderService;

    @Inject
    EmailService emailService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance orders(List<OrderData> orders);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderOrderTemplate() {
        return Templates.orders(getOrders());
    }

    private List<OrderData> getOrders() {
        return orderRepository.listAll()
                .stream()
                .map(OrderData::from)
                .toList();
    }

    @POST
    public Response createOrder(@NotNull CreateOrderData orderRequest) {
        //user validation
        Long userId = orderRequest.userId();
        userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new WebApplicationException(Response.Status.BAD_REQUEST));

        //product validation
        orderRequest.items().forEach(item -> {
            Product product = productRepository.findByIdOptional(item.getProductId())
                    .orElseThrow(() -> new WebApplicationException(Response.Status.BAD_REQUEST));
            int stock = product.getStock();
            if (stock == 0 || stock < item.getQuantity()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        });

        //payment validation async (messaging service)
        paymentService.create(orderRequest.payment());

        //create order
        orderService.create(orderRequest);

        //send email
        emailService.sendEmail(null);

        return Response.status(Response.Status.CREATED).entity(orderRequest).build();
    }
}
