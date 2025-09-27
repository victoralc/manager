package app.domain.invoices;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/invoices")
public class InvoiceResource {

    @Inject
    InvoiceRepository invoiceRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderInvoiceTemplate() {
        return Templates.invoices(getInvoices());
    }

    public List<InvoiceData> getInvoices() {
        return invoiceRepository.findAll().list()
                .stream()
                .map(InvoiceData::from)
                .toList();
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance invoices(List<InvoiceData> invoices);
    }
}
