package app.domain.employees;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

}
