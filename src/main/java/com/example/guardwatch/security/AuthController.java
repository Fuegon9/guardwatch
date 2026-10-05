package com.example.guardwatch.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// ============================================================
// AuthController.java — Endpoint de autenticación.
//
// POST /auth/login
//   Body: { "username": "...", "password": "..." }
//   Response 200: { "token": "eyJ..." }
//   Response 401: credenciales incorrectas
//
// El token recibido debe usarse en todos los endpoints protegidos:
//   Header: Authorization: Bearer <token>
//
// Ejemplos de credenciales (password 12345 en BCrypt):
//   admin_gw       → ADMINISTRADOR
//   agente_gw      → AGENTE
//   cliente_vip_gw → CLIENTE_VIP
//   cliente_gw     → CLIENTE_NORMAL
// (Ver data-inicial.sql para insertar estos usuarios en la BD)
// ============================================================
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUserDetailsService uds;
    private final JwtTokenUtil jwt;

    public AuthController(AuthenticationManager authManager,
                          JwtUserDetailsService uds,
                          JwtTokenUtil jwt) {
        this.authManager = authManager;
        this.uds = uds;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        // Validación básica: sin usuario o clave no tiene sentido autenticar
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Debe enviar username y password"));
        }

        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            // AuthenticationException cubre: clave incorrecta, usuario inexistente
            // (Spring lo convierte en BadCredentials) y usuario deshabilitado.
            return ResponseEntity.status(401)
                .body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        UserDetails ud = uds.loadUserByUsername(username);
        String token = jwt.generateToken(ud);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
