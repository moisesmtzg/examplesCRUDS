package com.moisesmtzg.crudexamples.repositories;

import com.moisesmtzg.crudexamples.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}