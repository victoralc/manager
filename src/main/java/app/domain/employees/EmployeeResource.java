package app.domain.employees;

import app.domain.users.User;
import app.domain.users.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/employees")
public class EmployeeResource {
    @Inject
    Validator validator;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    UserRepository userRepository;

    @GET
    public Response getAllEmployees() {
        return Response.ok(Employee.listAll()).build();
    }

    @POST
    @Transactional
    public Response createEmployee(CreateEmployeeForm form) {
        var violations = validator.validate(form);
        if (!violations.isEmpty()) {
            var errors = violations.stream()
                    .collect(Collectors.toMap(v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage));
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        //create user and save in database
        User user = new User(form.email(), "randomPassword123", "ROLE_MEMBER");
        userRepository.persist(user);

        //create employee and save in database
        Employee employee = new Employee();
        employee.setFirstName(form.firstName());
        employee.setLastName(form.lastName());
        employee.setActive(true);
        employee.setUser(user);
        employee.setDepartment(form.department());
        employee.setPhoneNumber(form.phone());
        employeeRepository.persist(employee);

        return Response.ok(Employee.listAll()).build();
    }

}
