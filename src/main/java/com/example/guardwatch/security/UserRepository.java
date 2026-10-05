package com.example.guardwatch.security;

import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad User.
// findByUsername es llamado por JwtUserDetailsService durante el login
// y en cada request para validar el token JWT.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
