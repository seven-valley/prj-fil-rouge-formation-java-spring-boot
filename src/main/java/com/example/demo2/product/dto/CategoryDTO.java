package com.example.demo2.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class CategoryDTO {


    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

}
