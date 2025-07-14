package app.domain.invoices;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/invoices")
public class InvoiceResource {

    @Inject
    InvoiceRepository invoiceRepository;

    @GET
    public Response getInvoices() {
        List<InvoiceData> invoices = invoiceRepository.findAll().list()
                .stream()
                .map(InvoiceData::from)
                .toList();
        return Response.ok(invoices).build();
    }
}
