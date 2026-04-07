package com.example.demo2.product.service;

import com.example.demo2.product.entity.*;
import com.example.demo2.product.dto.*;
import com.example.demo2.product.exception.*;
import com.example.demo2.product.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category introuvable"));

        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO create(CategoryDTO dto) {
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(repository.save(category));
    }

    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category introuvable"));

        existing.setName(dto.getName());
 
        return CategoryMapper.toDTO(repository.save(existing));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("category introuvable");
        }
        repository.deleteById(id);
    }
}