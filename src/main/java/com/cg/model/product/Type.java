package com.cg.model.product;

import com.cg.model.dto.productDTO.TypeDTO;
import com.cg.model.enums.EType;
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
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EType name;

    @OneToMany
    private List<Product> products;

    public TypeDTO toTypeDTO() {
        return new TypeDTO()
                .setId(id)
                .setCode(code);
    }

}
