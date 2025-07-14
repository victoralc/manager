package app.domain.invoices;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceData(
        Long id,
        Long orderId,
        LocalDate date,
        LocalDate dueDate,
        BigDecimal amountPaid,
        String paymentStatus) {

    public static InvoiceData from(Invoice entity) {
        return new InvoiceData(entity.getId(), entity.getOrder().getId(),
                entity.getDate(), entity.getDueDate(), entity.getAmountPaid(), entity.getPaymentStatus());
    }
}
