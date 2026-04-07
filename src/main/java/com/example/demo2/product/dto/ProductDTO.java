package com.example.demo2.product.dto;


import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Positive(message = "Le prix doit être positif")
    private double price;

    @Min(value = 0, message = "La quantité doit être >= 0")
    private int quantity;
    private CategoryDTO category;
    private List<TagDTO> tags;
}