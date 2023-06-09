package com.cg.service.brand;

import com.cg.model.Role;
import com.cg.model.product.Brand;
import com.cg.service.IGeneralService;

public interface IBrandService extends IGeneralService<Brand> {
    Brand findByName(String name);
}
