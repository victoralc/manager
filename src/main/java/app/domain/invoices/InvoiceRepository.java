package app.domain.invoices;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {
}
