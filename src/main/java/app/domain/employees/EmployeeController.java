package app.domain.employees;

import app.domain.users.User;
import app.domain.users.UserRepository;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/employees")
public class EmployeeController {

    @Inject
    private EmployeeRepository employeeRepository;
    @Inject
    private UserRepository userRepository;

    private static void validateEmployeeData(NewEmployeeData employeeData) {
        if (employeeData.firstName() == null || employeeData.firstName().isEmpty())
            throw new RuntimeException("Invalid firstName data. Cannot be null or empty");
        if (employeeData.lastName() == null || employeeData.lastName().isEmpty())
            throw new RuntimeException("Invalid lastName data. Cannot be null or empty");
        if (employeeData.department() == null || employeeData.department().isEmpty())
            throw new RuntimeException("Invalid department data. Cannot be null or empty");
        if (employeeData.email() == null || employeeData.email().isEmpty())
            throw new RuntimeException("Not a valid email.");
    }

    @GET
    @Path("/new")
    public String newEmployee() {
        return "create-employee";
    }

    @POST
    public String createEmployee(NewEmployeeData employeeData) {
        validateEmployeeData(employeeData);

        //create user and save in database
        User user = new User(employeeData.email(), "randomPassword123", "ROLE_MEMBER");
        userRepository.persist(user);

        //create employee and save in database
        Employee employee = new Employee();
        employee.setFirstName(employeeData.firstName());
        employee.setLastName(employeeData.lastName());
        employee.setActive(true);
        employee.setUser(user);
        employee.setDepartment(employeeData.department());
        employee.setPhoneNumber(employeeData.phoneNumber());
        employeeRepository.persist(employee);

        return "redirect:/employees";
    }

}
