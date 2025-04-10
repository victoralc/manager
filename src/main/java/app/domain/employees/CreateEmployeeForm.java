package app.domain.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.FormParam;

public record CreateEmployeeForm(
        @FormParam("firstName") @NotBlank(message = "First name must not be empty") String firstName,
        @FormParam("lastName") @NotBlank(message = "Last name must not be empty") String lastName,
        @FormParam("email") @Email @NotBlank(message = "Email must not be empty") String email,
        @FormParam("department") @NotBlank(message = "Department must not be empty") String department,
        @FormParam("phoneNumber") String phone) {
}