package com.example.demo2.product.entity;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Getters et Setters obligatoires pour JPA
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Méthodes de l'interface UserDetails
    @Override
    public String getUsername() { return email; } // On utilise l'email comme login

    @Override
    public String getPassword() { return password; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Vous pourrez ajouter des rôles ici plus tard
    }
}