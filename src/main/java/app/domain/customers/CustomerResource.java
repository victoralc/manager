package app.domain.customers;

import app.domain.customers.model.Customer;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @GET
    public Response renderCustomersTemplate() {
        List<Customer> customerList = customerRepository.listAll();
        return Response.ok(customerList).build();
    }

    @POST
    public Response create(@NotNull CreateCustomer request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setAddress(request.address());
        customerRepository.persist(customer);
        return Response.ok(customer).build();
    }
}
