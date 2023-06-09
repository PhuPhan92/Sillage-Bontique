package com.cg.repository;

import com.cg.model.dto.productDTO.ProductDTO;
import com.cg.model.product.Brand;
import com.cg.model.product.Product;
import com.cg.model.product.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "pro.id, " +
            "pro.title, " +
            "pro.price, " +
            "pro.quantity, " +
            "pro.description, " +
            "pro.productAvatar, " +
            "pro.brand, " +
            "pro.type " +
            ") " +
            "FROM Product AS pro " +
            "WHERE pro.deleted = false"
    )
    Page<ProductDTO> findAllProductByDeletedFalse(Pageable pageable);


    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND p.id = :id"
    )
    Optional<ProductDTO> findProductById(@Param("id") Long id);

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND p.type = :type"
    )
    List<ProductDTO> findAllProductByType(@Param("type") Type type);

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND p.brand = :brand"
    )
    List<ProductDTO> findAllProductByBrand(@Param("brand") Brand brand);

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND (p.title like :keyword OR p.description like :keyword ) " +
            "and (:brandId is null or:brandId = p.brand.id)" +
            "and (:typeId is null or :typeId = p.type.id)"
    )

    Page<ProductDTO> findAllByKeyword(@Param("keyword") String keyword,@Param("typeId") Long typeId,@Param("brandId") Long brandId, Pageable pageable);
    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND (p.title like :keyword OR p.description like :keyword ) " +
            "and (:typeId is null or :typeId = p.type.id)"
    )

    Page<ProductDTO> findAllByKeywordAndTypeId(@Param("keyword") String keyword,@Param("typeId") Long typeId, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND (p.title like :keyword OR p.description like :keyword ) " +
            "and (:brandId = p.brand.id)"
    )

    Page<ProductDTO> findAllByKeywordAndBrandId(@Param("keyword") String keyword,@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.productDTO.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.productAvatar, " +
            "p.brand, " +
            "p.type " +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND (p.title like :keyword OR p.description like :keyword ) "
    )
    Page<ProductDTO> findAllByOnlyKeyword(@Param("keyword") String keyword, Pageable pageable);
    @Modifying
    @Query("UPDATE Product AS p SET p.deleted = true WHERE p.id = :productId")
    void softDelete(@Param("productId") long productId);

    Boolean existsProductByTitle (String title);
    Boolean existsProductByTitleAndIdNot (String title, Long id);



}
