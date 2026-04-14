```java
import java.util.*;

public class Solution {

    public static int next(int n) {
        // Récupérer tous les chiffres de n
        String s = String.valueOf(n);
        Set<Character> forbidden = new HashSet<>();
        for (char c : s.toCharArray()) forbidden.add(c);

        int length = s.length();
        // Commencer à partir de n + 1
        for (long i = (long)n + 1; i < Math.pow(10, length + 1); i++) {
            String str = String.valueOf(i);
            boolean valid = true;
            for (char c : str.toCharArray()) {
                if (forbidden.contains(c)) {
                    valid = false;
                    break;
                }
            }
            if (valid) return (int)i;
        }

        return -1; // aucun entier valide
    }

    public static void main(String[] args) {
        System.out.println(next(654321)); // 700000
        System.out.println(next(123));    // 400
        System.out.println(next(987654321)); // -1 si impossible
    }
}
```

```java
import java.util.*;

public class TopKFrequent {

    public static List<Integer> topKFrequent(int[] nums, int k) {

        // 1. Compter les fréquences
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 2. Transformer en liste
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(freqMap.entrySet());

        // 3. Trier par fréquence décroissante
        list.sort((a, b) -> b.getValue() - a.getValue());

        // 4. Récupérer les k premiers
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(list.get(i).getKey());
        }

        return result;
    }

    // Test rapide
    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,3};
        int k = 2;

        System.out.println(topKFrequent(nums, k)); // [1, 2]
    }
}
```

Entity
```java
package com.example.demo2.product.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)    
    private String name;
    @Column(nullable = false)  
    private double price;
    @Column(nullable = false)  
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

     @ManyToMany

     @JoinTable(
        name = "product_tag",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
```

```java
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
```


```java
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
```

```java
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
```

```java
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
}
```