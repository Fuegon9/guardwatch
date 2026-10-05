package com.example.guardwatch.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// ============================================================
// Role.java — Entidad que representa un rol del sistema.
//
// Los cuatro roles de GuardWatch son:
//   ADMINISTRADOR  → acceso total a todos los endpoints
//   AGENTE         → todo lo relacionado con rutas, cargas,
//                    vehículos, zonas de riesgo e incidentes
//   CLIENTE_VIP    → puede consultar Y editar servicios de resguardo
//   CLIENTE_NORMAL → solo puede consultar (GET)
//
// El nombre del rol se guarda SIN el prefijo "ROLE_" en la BD.
// Spring Security lo añade automáticamente en JwtUserDetailsService
// al construir el SimpleGrantedAuthority("ROLE_" + role.getName()).
// ============================================================
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Valores válidos: ADMINISTRADOR | AGENTE | CLIENTE_VIP | CLIENTE_NORMAL
    @Column(unique = true, nullable = false)
    private String name;
}
