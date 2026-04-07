package com.example.demo2.product;


import org.springframework.web.bind.annotation.*;
import com.example.demo2.product.service.*;

import jakarta.validation.Valid;

import com.example.demo2.product.dto.*;

import java.util.List;



@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @PostMapping
    public CategoryDTO create(@Valid @RequestBody CategoryDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}