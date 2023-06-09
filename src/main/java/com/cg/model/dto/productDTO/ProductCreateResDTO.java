package com.cg.model.dto.productDTO;

import com.cg.model.product.Product;
import com.cg.model.product.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreateResDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;

    private ProductAvatarDTO productAvatar;

    private TypeDTO type;

    private BrandDTO brand;

    public ProductCreateResDTO(Product product, ProductAvatar productAvatar) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productAvatar = productAvatar.toProductAvatarDTO();
        this.type = product.getType().toTypeDTO();
        this.brand = product.getBrand().toBrandDTO();

    }
}
