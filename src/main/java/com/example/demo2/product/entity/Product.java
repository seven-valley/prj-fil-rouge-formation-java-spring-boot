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