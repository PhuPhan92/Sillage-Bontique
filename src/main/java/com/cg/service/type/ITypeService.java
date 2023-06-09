package com.cg.service.type;

import com.cg.model.product.Brand;
import com.cg.model.product.Type;
import com.cg.service.IGeneralService;

public interface ITypeService extends IGeneralService<Type> {
    Type findByName(String name);
}
