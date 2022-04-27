package com.underground.invoiceservice.repository;

import com.underground.invoiceservice.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    Optional<ProductModel> findByCode(String code);
}
