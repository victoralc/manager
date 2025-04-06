package app.domain.products;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/products")
public class ProductController {

    @Inject
    ProductRepository productRepository;

    @GET
    public String findProducts() {
        List<Product> products = productRepository.findAll().list();
        return "products";
    }

    @GET
    @Path("/new")
    public String newProduct() {
        return "create-product";
    }

}
