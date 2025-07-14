package app.domain.orders;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderData(
        Long customerId,
        List<OrderItemData> items,
        BigDecimal totalAmount,
        String paymentMethod,
        DeliveryData delivery) {
}
