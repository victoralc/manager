package app.domain.orders;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderData(
        Long userId,
        List<OrderItemData> items,
        BigDecimal totalAmount,
        PaymentData payment,
        DeliveryData delivery) {
}
