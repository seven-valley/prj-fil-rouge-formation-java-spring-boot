package com.example.demo2.product;

import com.example.demo2.product.dto.*;
import com.example.demo2.product.entity.*;
import com.example.demo2.product.repository.*;
import com.example.demo2.product.config.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Imports Spring Web pour le RestController
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
;

// Imports Spring Security pour l'authentification
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

// Imports de vos propres classes (à adapter selon vos packages)



// Import utilitaire pour la réponse JSON
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        // 1. Vérifier si l'email existe déjà
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erreur : Cet email est déjà utilisé !");
        }

        // 2. Créer le nouvel utilisateur
        User user = new User();
        user.setEmail(registrationDto.getEmail());

        // 3. HACHER le mot de passe (Indispensable !)
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // 4. Enregistrer en base de données
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegistrationDto loginDto) {
        // 1. Authentifier l'utilisateur
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Identifiants invalides");
        }

        // 2. Générer le Token
        String token = jwtService.generateToken(loginDto.getEmail());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
