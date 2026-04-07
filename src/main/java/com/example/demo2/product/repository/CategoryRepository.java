package com.example.demo2.product.repository;

import com.example.demo2.product.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   
}
