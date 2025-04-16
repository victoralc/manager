package app.domain.products;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    @Inject
    PhotoRepository photoRepository;

    public void savePhoto(Photo photo) {
        this.photoRepository.persist(photo);
    }
}
