package app.domain.customers.controller;

public record CustomerCreateForm(
        String name,
        String email,
        String address,
        String phone) {
}
