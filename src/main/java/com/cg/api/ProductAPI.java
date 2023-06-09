package com.cg.api;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.dto.productDTO.*;
import com.cg.model.product.Product;
import com.cg.model.product.Type;
import com.cg.model.product.Brand;
import com.cg.service.brand.IBrandService;
import com.cg.service.product.IProductService;
import com.cg.service.type.ITypeService;
import com.cg.utils.AppUtils;
import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private IProductService productService;

    @Autowired

    private ITypeService typeService;

    @Autowired
    private IBrandService brandService;
    @Autowired
    AppUtils appUtils;

    @PostMapping
    public ResponseEntity<?> create(@Validated ProductCreateReqDTO productCreateReqDTO, BindingResult bindingResult) {
        new ProductCreateReqDTO().validate(productCreateReqDTO, bindingResult);
        MultipartFile imageFile = productCreateReqDTO.getAvatarFile();

        if (imageFile == null) {
            throw new DataInputException("Vui lòng chọn hình ảnh!!");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (productService.existsProductByTitle(productCreateReqDTO.getTitle())) {
            throw new EmailExistsException("Sản phẩm đã tồn tại trong hệ thống!!");
        }

        productCreateReqDTO.setId(null);
        productCreateReqDTO.setQuantity("0");
        Product newProduct = productService.create(productCreateReqDTO);
        return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable) {

        Page<ProductDTO> productDTOS = productService.findAllProductByDeletedFalse(pageable);

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping("/type/{typeId}")
    public ResponseEntity<?> getAllByType(@PathVariable Long typeId) {
        Optional<Type> optionalType = typeService.findById(typeId);
        if (!optionalType.isPresent()) {
            throw new DataInputException("type is non-existent");
        }
        Type type = optionalType.get();

        List<ProductDTO> productDTOS = productService.findAllProductByType(type);
        if(productDTOS.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> getAllByBrand(@PathVariable Long brandId) {

        Optional<Brand> optionalBrand = brandService.findById(brandId);
        if (!optionalBrand.isPresent()) {
            throw new DataInputException("brand is non-existent");
        }
        Brand brand = optionalBrand.get();

        List<ProductDTO> productDTOS = productService.findAllProductByBrand(brand);
        if(productDTOS.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> getAllProductByKeyword(@RequestParam String keyword, @RequestParam(required = false) Long typeId, @RequestParam(required = false) Long brandId,@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable ){
        keyword = "%" + keyword + "%";
        var productDTOS = productService.findAllProductByKeyword(keyword, typeId,brandId, pageable);


        if(productDTOS.getContent().size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);

    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId) {

        Optional<ProductDTO> productDTO = productService.findProductById(productId);
        if (!productDTO.isPresent()) {
            throw new ResourceNotFoundException("Product not valid");
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long productId, MultipartFile file, @Validated ProductUpdateDTO productUpdateDTO, BindingResult bindingResult) {
        Optional<Product> productOptional = productService.findById(productId);
        if (!productOptional.isPresent()) {
            throw new DataInputException("ID sản phẩm không tồn tại!!");
        }

        Product product = productOptional.get();

        if (productService.existsProductByTitleAndIdNot(productUpdateDTO.getTitle(), productId)) {
            throw new DataInputException("Sản phẩm đã tồn tại trong hệ thống!!");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        String productName = productUpdateDTO.getTitle();
        BigDecimal price = new BigDecimal(Long.parseLong(productUpdateDTO.getPrice()));
        Long quantity = Long.parseLong(productUpdateDTO.getQuantity());
        String description = productUpdateDTO.getDescription();

        Optional <Type> OptionalType = typeService.findById(product.getType().getId());
        Type type = OptionalType.get();
        if (!OptionalType.isPresent()) {
            throw new DataInputException("type is non-existent");
        }

        Optional <Brand> OptionalBrand = brandService.findById(product.getBrand().getId());
        Brand brand = OptionalBrand.get();
        if (!OptionalBrand.isPresent()) {
            throw new DataInputException("Brand is non-existent");
        }

        product.setTitle(productName)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setType(type)
                .setBrand(brand);
        product = productService.save(product);

        if(file != null){
            product = productService.edit(product, file);
        }
//        if(file == null){
//            file = product.getProductAvatar()
//        }
        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }
//    @DeleteMapping("/delete/{productId}")
//    public ResponseEntity<?> delete(@PathVariable Long productId) throws IOException {
//
//        Optional<Product> productOptional = productService.findById(productId);
//
//        if (!productOptional.isPresent()) {
//            throw new ResourceNotFoundException("Product invalid");
//        }
//        Product product  = productOptional.get();
//        product.setDeleted(true);
//        productService.save(product);
//
//        return new ResponseEntity<>(product.toProductDTO(),HttpStatus.OK);
//    }
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("ID sản phẩm không hợp lệ!!");
        }

        try {
            productService.softDelete(productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Vui lòng liên hệ Administrator!!");
        }
    }



}
