package com.example.guardwatch.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

// ============================================================
// DataInitializer.java — Carga inicial de roles y usuarios.
//
// Se ejecuta UNA VEZ cada vez que arranca la aplicación, justo
// después de que Spring y JPA estén listos (CommandLineRunner).
//
// POR QUÉ EXISTE:
//   Antes los usuarios dependían de ejecutar data-inicial.sql a mano.
//   Si ese script no se ejecutaba (o se ejecutaba en otra BD), el login
//   fallaba con "Usuario o contraseña incorrectos" porque el usuario
//   simplemente no existía. Ahora la app los crea sola.
//
// ES IDEMPOTENTE: se puede reiniciar la app N veces sin duplicar nada.
//   - Si el rol/usuario no existe → lo crea.
//   - Si el usuario existe pero su clave no es 12345, o está deshabilitado,
//     o le falta su rol → lo corrige. (Esto es solo para desarrollo/demo.)
//
// La contraseña se guarda SIEMPRE como hash BCrypt, generado en el
// momento con el mismo PasswordEncoder que usa el login. Así el hash
// siempre coincide con el algoritmo en uso.
//
// USUARIOS DE PRUEBA (password: 12345):
//   admin_gw       → ADMINISTRADOR
//   agente_gw      → AGENTE
//   cliente_vip_gw → CLIENTE_VIP
//   cliente_gw     → CLIENTE_NORMAL
// ============================================================
@Component
public class DataInitializer implements CommandLineRunner {

    private static final String PASSWORD_INICIAL = "12345";

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(RoleRepository roleRepo,
                           UserRepository userRepo,
                           PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        // 1. Roles (nombre SIN el prefijo ROLE_; JwtUserDetailsService lo añade)
        Role administrador = asegurarRol("ADMINISTRADOR");
        Role agente        = asegurarRol("AGENTE");
        Role clienteVip    = asegurarRol("CLIENTE_VIP");
        Role clienteNormal = asegurarRol("CLIENTE_NORMAL");

        // 2. Usuarios con su rol
        asegurarUsuario("admin_gw",       administrador);
        asegurarUsuario("agente_gw",      agente);
        asegurarUsuario("cliente_vip_gw", clienteVip);
        asegurarUsuario("cliente_gw",     clienteNormal);

        System.out.println("=== [GuardWatch] Roles y usuarios listos. Password de prueba: "
                + PASSWORD_INICIAL + " ===");
    }

    // Devuelve el rol; si no existe en la BD lo crea.
    private Role asegurarRol(String nombre) {
        Role r = roleRepo.findByName(nombre);
        if (r == null) {
            r = new Role();
            r.setName(nombre);
            r = roleRepo.save(r);
            System.out.println("[SEED] Rol creado: " + nombre);
        }
        return r;
    }

    // Crea el usuario si no existe; si existe, garantiza clave, estado y rol.
    private void asegurarUsuario(String username, Role rol) {
        User u = userRepo.findByUsername(username);
        boolean cambiado = false;

        if (u == null) {
            u = new User();
            u.setUsername(username);
            u.setPassword(encoder.encode(PASSWORD_INICIAL));
            u.setEnabled(true);
            u.setRoles(new HashSet<>(Set.of(rol)));
            userRepo.save(u);
            System.out.println("[SEED] Usuario creado: " + username + " -> " + rol.getName());
            return;
        }

        // El usuario ya existía: corregir lo que esté mal
        if (!encoder.matches(PASSWORD_INICIAL, u.getPassword())) {
            u.setPassword(encoder.encode(PASSWORD_INICIAL));
            cambiado = true;
        }
        if (!Boolean.TRUE.equals(u.getEnabled())) {
            u.setEnabled(true);
            cambiado = true;
        }
        boolean tieneRol = u.getRoles() != null
                && u.getRoles().stream().anyMatch(r -> rol.getName().equals(r.getName()));
        if (!tieneRol) {
            Set<Role> roles = u.getRoles() != null ? new HashSet<>(u.getRoles()) : new HashSet<>();
            roles.add(rol);
            u.setRoles(roles);
            cambiado = true;
        }
        if (cambiado) {
            userRepo.save(u);
            System.out.println("[SEED] Usuario corregido: " + username);
        }
    }
}
