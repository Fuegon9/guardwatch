package com.example.guardwatch.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

// ============================================================
// User.java — Entidad de usuario del sistema.
//
// Representa a quien puede autenticarse en la API.
// Un usuario puede tener uno o más roles (ManyToMany).
// La contraseña se almacena SIEMPRE como hash BCrypt;
// nunca en texto plano (si se guarda en plano el login falla).
//
// Tabla generada en BD:
//   users        → id, username, password, enabled
//   roles        → id, name
//   user_role    → user_id, role_id  (tabla puente)
// ============================================================
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // Hash BCrypt — nunca texto plano
    @Column(nullable = false)
    private String password;

    private Boolean enabled = true;

    // EAGER: los roles se cargan junto al usuario porque Spring Security
    // los necesita inmediatamente al construir el UserDetails
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
