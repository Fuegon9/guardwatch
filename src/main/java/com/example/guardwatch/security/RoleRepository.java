package com.example.guardwatch.security;

import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Role.
// findByName es útil si en el futuro se quiere asignar roles
// dinámicamente desde un endpoint de administración.
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
