package com.cg.model.dto.productDTO;

import com.cg.model.product.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    @NotNull(message = "Type product is required")
    private Long id;

    private String code;

    public Type toType() {
        return new Type()
                .setId(id)
                .setCode(code);
    }
}
