package com.cg.repository;

import com.cg.model.product.Brand;
import com.cg.model.enums.EBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
}