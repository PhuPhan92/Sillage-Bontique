package com.cg.service.type;


import com.cg.model.product.Type;
import com.cg.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements ITypeService{
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return typeRepository.existsById(id);
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void delete(Type type) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }
}
