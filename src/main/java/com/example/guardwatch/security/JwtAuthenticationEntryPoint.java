package com.example.guardwatch.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// ============================================================
// JwtAuthenticationEntryPoint.java
//
// Se ejecuta cuando un request llega sin token o con token
// inválido a un endpoint protegido.
// Devuelve HTTP 401 Unauthorized con un mensaje claro.
// ============================================================
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException e) throws IOException {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Acceso no autorizado. Incluye un token JWT válido en el header Authorization.");
    }
}
