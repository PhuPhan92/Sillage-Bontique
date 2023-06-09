package com.cg.model.dto.productDTO;

import com.cg.model.product.Brand;
import com.cg.model.product.Product;
import com.cg.model.product.ProductAvatar;
import com.cg.model.product.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreateReqDTO implements Validator {
    private Long id;

    @Pattern(regexp = "^\\d+$", message = "Giá sản phẩm phải là số.")
    @NotEmpty(message = "Giá sản phẩm không được để trống.")
    private String price;
    @Size(min = 5, max = 100, message = "Tên sản phẩm có độ dài nằm trong khoảng 5 - 100 ký tự.")
    private String title;
    private String quantity;
    @NotEmpty(message = "Vui lòng nhập mô tả sản phẩm.")
    @Size(min = 5, max = 200, message = "Mô tả sản phẩm có độ dài nằm trong khoảng 5 - 200 ký tự.")
    private String description;
    private Long typeId;
    private Long brandId;
    private MultipartFile avatarFile;
    public Product toProduct(ProductAvatar productAvatar, Brand brand, Type type){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setQuantity(Long.parseLong(quantity))
                .setDescription(description)
                .setBrand(brand)
                .setType(type)
                .setProductAvatar(productAvatar);

    }
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreateReqDTO productCreateDTO = (ProductCreateReqDTO) target;
        String price = productCreateDTO.getPrice();
        if (price != null && price.length() > 0) {
            if (price.length() > 9) {
                errors.rejectValue("price", "price.max", "Giá sản phẩm tối đa là 999.999.999 VNĐ");
                return;
            }

            if (price.length() < 6) {
                errors.rejectValue("price", "price.min", "Giá sản phẩm thấp nhất là 100.000 VNĐ");
                return;
            }

            if (!price.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("price", "price.number", "Giá sản phẩm phải là số.");
                return;
            }

        } else {
            errors.rejectValue("price", "price.null", "Vui lòng nhập giá sản phẩm.");
        }
    }
}
