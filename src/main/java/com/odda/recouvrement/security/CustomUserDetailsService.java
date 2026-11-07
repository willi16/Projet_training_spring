package com.odda.recouvrement.security;

import com.odda.recouvrement.entities.Personnel;
import com.odda.recouvrement.repositories.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonnelRepository personnelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personnel personnel = personnelRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        if (!personnel.isActif()) {
            throw new UsernameNotFoundException("Ce compte est désactivé.");
        }

        // Spring Security exige le préfixe "ROLE_" pour hasRole()
        String authority = "ROLE_" + personnel.getRole().name();
        
        return new User(
                personnel.getUsername(),
                personnel.getPassword(),
                personnel.isActif(),
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
}


