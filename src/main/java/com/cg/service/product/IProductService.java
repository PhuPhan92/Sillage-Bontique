package com.cg.service.product;

import com.cg.model.dto.productDTO.ProductCreateReqDTO;
import com.cg.model.dto.productDTO.ProductDTO;

import com.cg.model.product.Brand;
import com.cg.model.product.Product;
import com.cg.model.product.Type;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {
    Product create(ProductCreateReqDTO productCreateReqDTO);
    Product edit (Product product, MultipartFile file);
    Page<ProductDTO> findAllProductByDeletedFalse(Pageable pageable);
    List<ProductDTO> findAllProductByType(Type type);
    List<ProductDTO> findAllProductByBrand(Brand brand);
    Page<ProductDTO> findAllProductByKeyword(String keyword, Long typeId, Long brandId, Pageable pageable);
    Page<ProductDTO> findAllByKeywordAndTypeId(String keyword, Long typeId, Pageable pageable);
    Page<ProductDTO> findAllByKeywordAndBrandId(String keyword, Long brandId, Pageable pageable);
    Page<ProductDTO> findAllByOnlyKeyword(String keyword, Pageable pageable);
    Optional<ProductDTO> findProductById(Long id);
    Boolean existsProductByTitle (String title);
    Boolean existsProductByTitleAndIdNot (String title, Long id);
    void softDelete(Long productId);
//    ProductUpdateResDTO updateWithAvatar(Product product, MultipartFile avatarFile) throws IOException;
}
