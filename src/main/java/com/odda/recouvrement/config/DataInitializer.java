package com.odda.recouvrement.config;

import com.odda.recouvrement.entities.Personnel;
import com.odda.recouvrement.enums.RoleUtilisateur;
import com.odda.recouvrement.repositories.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PersonnelRepository personnelRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            // Vérifie si l'admin existe déjà pour ne pas le recréer à chaque démarrage
            if (!personnelRepository.existsByUsernameOrEmail("admin", "admin")) {
                
                Personnel admin = Personnel.builder()
                        .username("admin")
                        // C'est JAVA qui hache le mot de passe ici. Zéro risque d'erreur !
                        .password(passwordEncoder.encode("admin123")) 
                        .nom("Super")
                        .prenom("Administrateur")
                        .email("admin@ecole.com")
                        .matricule("ADM-001")
                        .role(RoleUtilisateur.ADMIN)
                        .actif(true)
                        .build();
                
                personnelRepository.save(admin);
                System.out.println("=================================================================");
                System.out.println("✅ COMPTE ADMIN CRÉÉ AVEC SUCCÈS !");
                System.out.println("👤 Username: admin");
                System.out.println("🔑 Password: admin123");
                System.out.println("=================================================================");
            }
        };
    }
}