package com.example.demo2.product.service;

import com.example.demo2.product.dto.*;
import com.example.demo2.product.entity.*;

public class CategoryMapper {
    
    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
