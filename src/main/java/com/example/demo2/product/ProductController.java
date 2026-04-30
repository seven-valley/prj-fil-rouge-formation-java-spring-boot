package com.example.demo2.product;

import org.springframework.web.bind.annotation.*;
import com.example.demo2.product.service.*;

import jakarta.validation.Valid;

import com.example.demo2.product.dto.*;

import java.util.List;



@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @PostMapping
    public ProductDTO create(@Valid @RequestBody ProductDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
     @GetMapping("/category/{categoryId}")
    public List<ProductDTO>getByCategory(@PathVariable Long categoryId) {
        System.out.println("aa");
        return service.getByCategory(categoryId);
    }
    @GetMapping("search/{lettres}")
    public List<ProductDTO> getLetter(@PathVariable String lettres) {
        return service.search(lettres);
    }
}