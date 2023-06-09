package com.cg.model.product;

import com.cg.model.dto.productDTO.BrandDTO;
import com.cg.model.enums.EBrand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBrand name;

    @OneToMany
    private List<Product> products;

    public BrandDTO toBrandDTO() {
        return new BrandDTO()
                .setId(id)
                .setCode(code);
    }

}
