package com.example.guardwatch.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// ============================================================
// JwtRequestFilter.java — Intercepta cada request HTTP.
//
// FLUJO por cada petición:
//   1. Lee el header "Authorization"
//   2. Si empieza con "Bearer " extrae el token
//   3. Extrae el username del payload del JWT
//   4. Carga el usuario desde la BD (con sus roles)
//   5. Valida el token (firma + expiración + username)
//   6. Registra la autenticación en el SecurityContext
//   7. Spring Security aplica las reglas de acceso definidas
//      en WebSecurityConfig según el rol del usuario
//
// Si el token falta o es inválido, el contexto queda sin
// autenticación y Spring devuelve 401 Unauthorized.
// ============================================================
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService uds;
    private final JwtTokenUtil jwt;

    public JwtRequestFilter(JwtUserDetailsService uds, JwtTokenUtil jwt) {
        this.uds = uds;
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String username = jwt.extractUsername(token);
                if (username != null
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails ud = uds.loadUserByUsername(username);
                    if (jwt.validateToken(token, ud)) {
                        UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                ud, null, ud.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception e) {
                // Token inválido o expirado → continuamos sin autenticación
            }
        }
        chain.doFilter(req, resp);
    }
}
