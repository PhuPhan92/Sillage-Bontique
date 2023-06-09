package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.dto.productDTO.ProductCreateReqDTO;
import com.cg.model.dto.productDTO.ProductDTO;
import com.cg.model.product.Product;
import com.cg.model.product.Type;
import com.cg.model.product.Brand;
import com.cg.model.product.ProductAvatar;
import com.cg.repository.ProductAvatarRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.brand.BrandServiceImpl;
import com.cg.service.productAvatar.ProductAvatarServiceImpl;
import com.cg.service.type.TypeServiceImpl;
import com.cg.service.uploadMedia.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAvatarServiceImpl productAvatarService;
    @Autowired
    private ProductAvatarRepository productAvatarRepository;
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    private BrandServiceImpl brandService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UploadUtils uploadUtils;


    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product create(ProductCreateReqDTO productCreateReqDTO) {

        MultipartFile file = productCreateReqDTO.getAvatarFile();
        String fileType = file.getContentType();
        assert fileType != null;
        fileType = fileType.substring(0, 5);

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatar.setFileType(fileType);
        productAvatar = productAvatarRepository.save(productAvatar);
        uploadAndSaveProductAvatar(file, productAvatar);
        Optional <Type> OptionalType = typeService.findById(productCreateReqDTO.getTypeId());
        Type type = OptionalType.get();
        if (!OptionalType.isPresent()) {
            throw new DataInputException("type is non-existent");
        }

        Optional <Brand> OptionalBrand = brandService.findById(productCreateReqDTO.getBrandId());
        Brand brand = OptionalBrand.get();
        if (!OptionalBrand.isPresent()) {
            throw new DataInputException("Brand is non-existent");
        }


        Product product = productCreateReqDTO.toProduct(productAvatar, brand, type);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public Product edit(Product product, MultipartFile file) {
        ProductAvatar oldProductAvatar = product.getProductAvatar();
        try {
            uploadService.destroyImage(oldProductAvatar.getCloudId(), uploadUtils.buildProductImageDestroyParams(product, oldProductAvatar.getCloudId()));
            productAvatarService.deleteById(oldProductAvatar.getId());
            String fileType = file.getContentType();
            assert fileType != null;
            fileType = fileType.substring(0, 5);
            ProductAvatar productAvatar = new ProductAvatar();
            productAvatar.setFileType(fileType);
            productAvatar = productAvatarRepository.save(productAvatar);

            uploadAndSaveProductAvatar(file, productAvatar);

            product.setProductAvatar(productAvatar);
            product = productRepository.save(product);
            return product;
        } catch (IOException e) {
            throw new DataInputException("Xóa hình ảnh thất bại.");
        }
    }

    private void uploadAndSaveProductAvatar(MultipartFile file, ProductAvatar productAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildProductImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(uploadUtils.PRODUCT_IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            productAvatarService.save(productAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
    @Override
    public Page<ProductDTO> findAllProductByDeletedFalse(Pageable pageable) {
        return productRepository.findAllProductByDeletedFalse(pageable);
    }
    @Override
    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findProductById(id);
    }
    @Override
    public List<ProductDTO> findAllProductByType(Type type) {
        return productRepository.findAllProductByType(type);
    }
    @Override
    public List<ProductDTO> findAllProductByBrand(Brand brand) {
        return productRepository.findAllProductByBrand(brand);
    }

    @Override
    public Page<ProductDTO> findAllProductByKeyword(String keyword, Long typeId, Long brandId, Pageable pageable) {
        return productRepository.findAllByKeyword(keyword,typeId, brandId, pageable);
    }
    @Override
    public Page<ProductDTO> findAllByKeywordAndTypeId(String keyword, Long typeId, Pageable pageable) {
        return productRepository.findAllByKeywordAndTypeId(keyword,typeId, pageable);
    }
    @Override
    public Page<ProductDTO> findAllByKeywordAndBrandId(String keyword, Long brandId, Pageable pageable) {
        return productRepository.findAllByKeywordAndBrandId(keyword, brandId, pageable);
    }
    @Override
    public Page<ProductDTO> findAllByOnlyKeyword(String keyword, Pageable pageable) {
        return productRepository.findAllByOnlyKeyword(keyword, pageable);
    }


    @Override
    public Boolean existsProductByTitle(String title) {
        return productRepository.existsProductByTitle(title);
    }

    @Override
    public Boolean existsProductByTitleAndIdNot(String title, Long id) {
        return productRepository.existsProductByTitleAndIdNot(title,id);
    }
    @Override
    public void softDelete(Long productId) {
        productRepository.softDelete(productId);
    }
//
//    @Override
//    public ProductUpdateResDTO updateWithAvatar(Product product, MultipartFile avatarFile) throws IOException {
//        return null;
//    }
}
