package com.example.demo2.product.repository;

import com.example.demo2.product.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByCategoryId(Long categoryId);
     List<Product> findByNameContaining(String name);
}