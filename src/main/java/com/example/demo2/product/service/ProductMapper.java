package com.example.demo2.product.service;


import com.example.demo2.product.dto.*;
import com.example.demo2.product.entity.*;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        CategoryDTO catDTO = new CategoryDTO();
        catDTO.setId(product.getCategory().getId());
        catDTO.setName(product.getCategory().getName());

        dto.setCategory(catDTO);

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        Category category = new Category();
        category.setId(dto.getCategory().getId());

        product.setCategory(category);

        return product;
    }
}