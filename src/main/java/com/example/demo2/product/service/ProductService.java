package com.example.demo2.product.service;

import com.example.demo2.product.entity.*;
import com.example.demo2.product.dto.*;
import com.example.demo2.product.exception.*;
import com.example.demo2.product.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
     @Autowired
    private  ProductRepository repository;
     @Autowired
    private CategoryRepository repositoryCateg;



    public List<ProductDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public ProductDTO getById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable"));

        return ProductMapper.toDTO(product);
    }

    public ProductDTO create(ProductDTO dto) {
         Category category = repositoryCateg.findById(dto.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategory().getId()));
           // System.out.println("test");
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(category);
        
        return ProductMapper.toDTO(repository.save(product));
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setQuantity(dto.getQuantity());

        return ProductMapper.toDTO(repository.save(existing));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produit introuvable");
        }
        repository.deleteById(id);
    }
    
 public List<ProductDTO> getByCategory(Long categoryId) {

        List<Product> products = repository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this category");
        }

        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }
}