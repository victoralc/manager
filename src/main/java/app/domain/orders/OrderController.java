package app.domain.orders;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
public class OrderController {
    @Inject
    OrderRepository orderRepository;

    @GET
    public Response getOrders() {
        List<Order> orders = orderRepository.listAll();
        return Response.ok(orders).build();
    }

    @POST
    public Response createOrder(@NotNull CreateOrderData createOrder) {
        return Response.status(Response.Status.CREATED).entity(createOrder).build();
    }
}
