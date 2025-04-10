package app.domain.employees;

import app.domain.users.User;
import app.domain.users.UserRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/employees")
public class EmployeeResource {
    @Inject
    Validator validator;
    @Inject
    private EmployeeRepository employeeRepository;
    @Inject
    private UserRepository userRepository;

    @GET
    @Path("/create")
    public TemplateInstance newEmployee() {
        return Templates.create().data("form", null).data("errors", Map.of());
    }

    @GET
    @Blocking
    public TemplateInstance list() {
        return Templates.list(Employee.listAll());
    }

    @POST
    @Blocking
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance createEmployee(CreateEmployeeForm form) {
        var violations = validator.validate(form);
        if (!violations.isEmpty()) {
            Map<String, String> errors = violations.stream()
                    .collect(Collectors
                            .toMap(v -> v.getPropertyPath().toString(),
                                    ConstraintViolation::getMessage));
            return Templates.create().data("errors", errors).data("form", form);
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

        return Templates.list(Employee.listAll());
    }

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance list(List<Employee> employees);

        public static native TemplateInstance create();
    }

}
