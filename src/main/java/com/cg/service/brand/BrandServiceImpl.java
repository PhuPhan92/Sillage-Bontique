package com.cg.service.brand;

import com.cg.model.Role;

import com.cg.model.product.Brand;
import com.cg.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements IBrandService{
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return brandRepository.existsById(id);
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Brand brand) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }
}
