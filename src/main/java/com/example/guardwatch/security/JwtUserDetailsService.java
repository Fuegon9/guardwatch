package com.example.guardwatch.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

// ============================================================
// JwtUserDetailsService.java — Carga el usuario desde la BD
// para que Spring Security pueda verificar credenciales.
//
// Spring Security llama a loadUserByUsername() en dos momentos:
//   1. Durante el login (AuthController) para verificar username+password
//   2. En JwtRequestFilter para cargar los roles del token JWT
//
// Los roles se mapean a GrantedAuthority con el prefijo "ROLE_":
//   "ADMINISTRADOR"  → ROLE_ADMINISTRADOR
//   "AGENTE"         → ROLE_AGENTE
//   "CLIENTE_VIP"    → ROLE_CLIENTE_VIP
//   "CLIENTE_NORMAL" → ROLE_CLIENTE_NORMAL
//
// Spring Security usa este prefijo en hasRole("ADMINISTRADOR"),
// que equivale a hasAuthority("ROLE_ADMINISTRADOR").
// ============================================================
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public JwtUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
            u.getUsername(),
            u.getPassword(),
            u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toList())
        );
    }
}
