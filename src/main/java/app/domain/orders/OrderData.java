package app.domain.orders;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderData(
        Long id,
        Long customerId,
        LocalDate orderDate,
        BigDecimal totalAmount,
        String status,
        Long orderDeliveryId) {

    public static OrderData from(Order order) {
        return new OrderData(order.getId(), order.getCustomer().getId(),
                order.getOrderDate(), order.getTotalAmount(),
                order.getStatus(), 1L);
    }
}
