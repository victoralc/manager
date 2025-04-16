package app.domain.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductForm(
        @FormParam("name") @NotBlank(message = "Name must not be empty") String name,
        @FormParam("description") @NotBlank(message = "Description must not be empty") String description,
        @FormParam("photos") @NotEmpty(message = "At least 1 image must be provided") List<FileUpload> photos,
        @FormParam("inStock") int inStock,
        @FormParam("price") @NotNull BigDecimal price) {
}
