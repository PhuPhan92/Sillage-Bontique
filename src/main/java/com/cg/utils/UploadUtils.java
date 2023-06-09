package com.cg.utils;

import com.cg.exception.DataInputException;
import com.cg.model.product.Product;
import com.cg.model.product.ProductAvatar;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtils {
    public final String CUSTOMER_IMAGE_UPLOAD_FOLDER = "c1022_customer_images";
    public final String CUSTOMER_VIDEO_UPLOAD_FOLDER = "c1022_customer_videos";
    public final String PRODUCT_IMAGE_UPLOAD_FOLDER = "c1022_product_manager_images";
    public final String PRODUCT_VIDEO_UPLOAD_FOLDER = "c1022_product_manager_videos";


    public Map buildProductImageUploadParams(ProductAvatar productAvatar) {
        if (productAvatar == null || productAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", PRODUCT_IMAGE_UPLOAD_FOLDER, productAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildProductImageDestroyParams(Product product, String publicId) {
        if (product == null || product.getId() == null)
            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

}
