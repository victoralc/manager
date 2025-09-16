package app.domain.customers;

import app.domain.customers.model.Customer;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;


@Path("/customers")
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance customers(List<Customer> customers);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderCustomersTemplate() {
        List<Customer> customerList = customerRepository.listAll();
        return Templates.customers(customerList);
    }

    @POST
    @Transactional
    public Response create(@NotNull CreateCustomer request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setAddress(request.address());
        customerRepository.persist(customer);
        return Response.ok(customer).build();
    }

    @DELETE
    @Transactional
    @Path("/{customerId}")
    public Response delete(@NotNull @PathParam("customerId") Long customerId) {
        customerRepository.deleteById(customerId);
        return Response.noContent().build();
    }
}
