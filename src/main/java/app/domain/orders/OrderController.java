package app.domain.orders;

import app.domain.payments.PaymentService;
import app.domain.products.Product;
import app.domain.products.ProductRepository;
import app.domain.users.UserRepository;
import app.service.EmailService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
public class OrderController {
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

    @GET
    public Response getOrders() {
        List<OrderData> orders = orderRepository.listAll()
                .stream()
                .map(OrderData::from)
                .toList();
        return Response.ok(orders).build();
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
