package com.example.demo2;


import com.example.demo2.product.service.*;
import com.example.demo2.product.entity.*;
import com.example.demo2.product.dto.*;
import com.example.demo2.product.repository.*;






import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProducts() {
        Category category = new Category(1L, "Test");
        Product product = new Product(1L, "Test", 10.0, 5, category,new ArrayList<>());

        when(productRepository.findAll()).thenReturn(List.of(
              product
        ));

        List<ProductDTO> result = productService.getAll();

        assertEquals(1, result.size());
    }
}