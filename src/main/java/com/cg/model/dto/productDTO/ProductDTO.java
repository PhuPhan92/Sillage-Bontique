package com.cg.model.dto.productDTO;

import com.cg.model.product.Brand;
import com.cg.model.product.Product;
//import com.cg.model.product.Type;
import com.cg.model.product.ProductAvatar;
import com.cg.model.product.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private ProductAvatarDTO productAvatar;
    private TypeDTO type;
    private BrandDTO brand;

    public ProductDTO(Long id, String title, BigDecimal price, Long quantity, String description, ProductAvatar productAvatar, Brand brand, Type type){
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.productAvatar = productAvatar.toProductAvatarDTO();
        this.brand = brand.toBrandDTO();
        this.type = type.toTypeDTO();

    }

    public Product toProduct() {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setPrice(price)
                .setQuantity(quantity)
                .setProductAvatar(productAvatar.toProductAvatar())
                .setType(type.toType())
                .setBrand(brand.toBrand())
                ;
    }
}
