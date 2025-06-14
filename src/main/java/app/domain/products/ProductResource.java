package app.domain.products;

import app.domain.employees.Employee;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Path("/products")
public class ProductResource {
    @Inject
    Validator validator;

    @Inject
    ProductRepository repository;

    @ConfigProperty(name = "photos.dir")
    String photosDir;

    @GET
    public Response findProducts() {
        List<Product> products = repository.findAll().list();
        return Response.ok(products).build();
    }

    @POST
    @Transactional
    public Response createProduct(CreateProductFormData form) throws IOException {
        var violations = validator.validate(form);
        if (!violations.isEmpty()) {
            var errors = violations.stream()
                    .collect(Collectors.toMap(v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage));
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        //create product
        Product product = new Product();
        product.setName(form.name());
        product.setDescription(form.description());
        product.setPrice(form.price());
        product.setStock(form.inStock());
        repository.persist(product);

        for (FileUpload file : form.photos()) {
            //persist photo data in database
            var photo = new Photo();
            var filename = generateUniqueFileName(file.fileName());
            photo.setName(filename);
            photo.setSize(file.size());
            photo.setUrl(photosDir.concat("/").concat(filename));
            photo.setProduct(product);
            repository.savePhoto(photo);

            //copy file in filesystem
            var path = file.uploadedFile();
            Files.copy(path, Paths.get(photosDir).resolve(filename), REPLACE_EXISTING);
        }

        return Response.ok(Employee.listAll()).build();
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
            extension = originalFileName.substring(dotIndex);
        }
        extension = extension.replaceAll("[^a-zA-Z0-9.]", "");
        return UUID.randomUUID() + extension;
    }

}
