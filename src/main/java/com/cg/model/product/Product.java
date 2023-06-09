package com.cg.model.product;


import com.cg.model.BaseEntity;
import com.cg.model.dto.productDTO.ProductCreateResDTO;
import com.cg.model.dto.productDTO.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String title;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    private Long  quantity;
    private String description;

    @OneToOne
    @JoinColumn(name = "product_avatar_id", referencedColumnName = "id", nullable = false)
    private ProductAvatar productAvatar;

    @ManyToOne
    @JoinColumn(name = "product_brand_id", referencedColumnName = "id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setPrice(price)
                .setTitle(title)
                .setQuantity(quantity)
                .setDescription(description)
                .setBrand(brand.toBrandDTO())
                .setType(type.toTypeDTO())
                .setProductAvatar(productAvatar.toProductAvatarDTO());
    }


}
