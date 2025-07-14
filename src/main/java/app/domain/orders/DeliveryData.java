package app.domain.orders;

public record DeliveryData(
        String street,
        String number,
        String postalCode,
        String complement,
        String city,
        String country) {
}
