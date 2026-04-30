package com.example.demo2.product.service;

import com.example.demo2.product.dto.*;
import com.example.demo2.product.entity.*;

public class TagMapper {
    
    public static Tag toEntity(TagDTO dto) {
        return Tag.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static TagDTO toDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}