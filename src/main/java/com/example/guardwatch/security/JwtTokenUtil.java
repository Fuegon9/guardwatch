package com.example.guardwatch.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HexFormat;

// ============================================================
// JwtTokenUtil.java — Genera y valida tokens JWT.
//
// FLUJO:
//   1. POST /auth/login → AuthController autentica credenciales
//   2. generateToken() crea un JWT firmado con HMAC-SHA256,
//      válido 5 horas, con el username como subject
//   3. El cliente envía el token en cada request:
//      Header: Authorization: Bearer <token>
//   4. JwtRequestFilter extrae el token, llama extractUsername()
//      para saber quién es, y validateToken() para confirmar
//      que no expiró y corresponde al mismo usuario
// ============================================================
@Component
public class JwtTokenUtil {

    // Se lee desde application.properties → jwt.secret
    // Cadena hexadecimal de 64 chars = 256 bits (mínimo para HMAC-SHA256)
    @Value("${jwt.secret}")
    private String secret;

    // Validez del token: 5 horas en milisegundos
    private static final long VALIDITY = 5 * 60 * 60 * 1000L;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(HexFormat.of().parseHex(secret));
    }

    // Genera el JWT con el username como subject
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + VALIDITY))
            .signWith(getKey())
            .compact();
    }

    // Extrae el username del payload del token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Verifica firma y expiración
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername())
                && !extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
