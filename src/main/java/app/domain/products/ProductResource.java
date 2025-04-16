package app.domain.products;

import app.domain.employees.Employee;
import app.domain.employees.EmployeeResource;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Path("/products")
@Blocking
public class ProductResource {
    @Inject
    Validator validator;

    @Inject
    ProductRepository repository;

    @ConfigProperty(name = "photos.dir")
    String photosDir;

    @GET
    public TemplateInstance findProducts() {
        List<Product> products = repository.findAll().list();
        return Templates.list(products);
    }

    @GET
    @Path("/new")
    public TemplateInstance newProduct() {
        return Templates.create().data("form", null).data("errors", Map.of());
    }

    @POST
    @Blocking
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public TemplateInstance createProduct(CreateProductForm form) throws IOException {
        var violations = validator.validate(form);
        if (!violations.isEmpty()) {
            var errors = violations.stream()
                    .collect(Collectors.toMap(v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage));
            return EmployeeResource.Templates.create().data("errors", errors).data("form", form);
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

        return Templates.list(Employee.listAll());
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

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance list(List<Product> products);

        public static native TemplateInstance create();
    }

}
