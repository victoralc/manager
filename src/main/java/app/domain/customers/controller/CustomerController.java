package app.domain.customers.controller;

import app.domain.customers.CustomerRepository;
import app.domain.customers.model.Customer;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
public class CustomerController {

    @Inject
    Template customers;

    @Inject
    CustomerRepository customerRepository;

    @GET
    @Blocking
    public TemplateInstance renderCustomersTemplate() {
        List<Customer> customerList = customerRepository.listAll();
        return customers.data("customers", customerList);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void add(@NotNull CustomerCreateForm form) {
        Customer customer = new Customer();
        customer.setName(form.name());
        customer.setEmail(form.email());
        customer.setPhone(form.phone());
        customer.setAddress(form.address());
    }
}
