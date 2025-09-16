package app.domain.orders;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    public void create(CreateOrderData order) {

    }
}
