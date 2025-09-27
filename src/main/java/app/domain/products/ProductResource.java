package app.domain.products;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {
    @Inject
    Validator validator;

    @Inject
    ProductRepository repository;

    @ConfigProperty(name = "photos.dir")
    String photosDir;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance products() {
        List<ProductData> products = repository
                .findAll()
                .list()
                .stream()
                .map(p -> new ProductData(
                        p.getId(), p.getName(),
                        p.getDescription(), p.getPrice(),
                        p.getStock())
                ).toList();
        return Templates.products(products);
    }

    @GET()
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newProduct() {
        return Templates.newProduct(CreateProductFormData.newProductFormData())
                .data("success", null)
                .data("error", null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public TemplateInstance createProduct(CreateProductFormData form) throws IOException {
        var violations = validator.validate(form);
        if (!violations.isEmpty()) {
            var errors = violations.stream()
                    .collect(Collectors.toMap(v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage));
            return Templates.newProduct(form)
                    .data("success", null)
                    .data("error", errors.values());
        }

        //create product
        Product product = new Product();
        product.setName(form.name());
        product.setDescription(form.description());
        product.setPrice(form.price());
        product.setStock(form.stock());
        repository.persist(product);

//        for (FileUpload file : form.photos()) {
//            //persist photo data in database
//            var photo = new Photo();
//            var filename = generateUniqueFileName(file.fileName());
//            photo.setName(filename);
//            photo.setSize(file.size());
//            photo.setUrl(photosDir.concat("/").concat(filename));
//            photo.setProduct(product);
//            repository.savePhoto(photo);
//
//            //copy file in filesystem
//            var path = file.uploadedFile();
//            Files.copy(path, Paths.get(photosDir).resolve(filename), REPLACE_EXISTING);
//        }

        return Templates.productForm(CreateProductFormData.newProductFormData())
                .data("error", null)
                .data("success", "Product created");
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
        public static native TemplateInstance products(List<ProductData> products);
        public static native TemplateInstance newProduct(CreateProductFormData product);
        public static native TemplateInstance productForm(CreateProductFormData product);
    }

}
