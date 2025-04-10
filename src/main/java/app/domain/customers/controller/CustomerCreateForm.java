package app.domain.customers.controller;

import jakarta.ws.rs.FormParam;

public record CustomerCreateForm(
        @FormParam("name") String name,
        @FormParam("email") String email,
        @FormParam("address") String address,
        @FormParam("phone") String phone) {
}
