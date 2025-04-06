package app.domain.orders;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/orders")
public class OrderController {
    @Inject
    private OrderRepository orderRepository;

    @GET
    public String getOrders() {
        List<Order> orders = orderRepository.listAll();
        return "orders";
    }
}
