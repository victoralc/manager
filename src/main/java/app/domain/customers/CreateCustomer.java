package app.domain.customers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateCustomer(
        @NotNull String name,
        @Email String email,
        @NotNull String address,
        @NotNull String phone) {
}
