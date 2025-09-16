package app.domain.products;

import java.math.BigDecimal;

public record ProductData(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock) {

}
