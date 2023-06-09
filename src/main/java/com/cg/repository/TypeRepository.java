package com.cg.repository;
import com.cg.model.product.Type;
import com.cg.model.enums.EType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
