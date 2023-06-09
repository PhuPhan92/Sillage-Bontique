package com.cg.model.dto.productDTO;

import com.cg.model.product.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    @NotNull(message = "Brand product is required")
    private Long id;

    private String code;

    public Brand toBrand() {
        return new Brand()
                .setId(id)
                .setCode(code);
    }
}
