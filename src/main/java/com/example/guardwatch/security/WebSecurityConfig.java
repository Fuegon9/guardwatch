package com.example.guardwatch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ============================================================
// WebSecurityConfig.java — Configuración central de seguridad.
//
// ROLES Y PERMISOS DE GUARDWATCH:
// ---------------------------------------------------------------
// ADMINISTRADOR  → acceso total: GET + POST + PUT + DELETE en todo
// AGENTE         → todo lo de su dominio operativo:
//                  rutas, cargas, vehículos, zonas de riesgo,
//                  agentes, incidentes, seguimientos, historiales,
//                  servicios de resguardo (solo GET y PUT en estos)
// CLIENTE_VIP    → puede consultar (GET) todo + editar (PUT)
//                  sus servicios de resguardo y cargas
// CLIENTE_NORMAL → solo puede consultar (GET) endpoints de
//                  servicios de resguardo, rutas y su historial
//
// REGLA: las reglas se evalúan en orden; la primera que coincide
// gana. Por eso los permisos más específicos van primero y
// anyRequest().authenticated() va al final.
// ============================================================
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtRequestFilter filter;
    private final JwtAuthenticationEntryPoint entryPoint;

    public WebSecurityConfig(JwtRequestFilter filter,
                             JwtAuthenticationEntryPoint entryPoint) {
        this.filter = filter;
        this.entryPoint = entryPoint;
    }

    // BCrypt para encriptar y verificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())

            // Sin estado: cada request se autentica con su token JWT
            .sessionManagement(s ->
                s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Respuesta 401 cuando falta el token
            .exceptionHandling(e ->
                e.authenticationEntryPoint(entryPoint))

            .authorizeHttpRequests(a -> a

                // ── Endpoints públicos ──────────────────────────────────
                // Login: cualquiera puede obtener su token
                .requestMatchers("/auth/login").permitAll()

                // Swagger: accesible sin token para revisar la API
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/api-docs/**"
                ).permitAll()

                // ── Agentes de seguridad (/api/agentes) ────────────────
                // Solo ADMINISTRADOR puede crear, modificar o eliminar agentes
                .requestMatchers(HttpMethod.POST,   "/api/agentes/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT,    "/api/agentes/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/agentes/**").hasRole("ADMINISTRADOR")
                // ADMINISTRADOR y AGENTE pueden consultar agentes
                .requestMatchers(HttpMethod.GET, "/api/agentes/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")

                // ── Clientes (/api/clientes) ────────────────────────────
                // Solo ADMINISTRADOR gestiona clientes
                .requestMatchers("/api/clientes/**").hasRole("ADMINISTRADOR")

                // ── Vehículos (/api/vehiculos) ──────────────────────────
                // AGENTE puede crear, consultar y actualizar vehículos
                .requestMatchers(HttpMethod.DELETE, "/api/vehiculos/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers("/api/vehiculos/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")

                // ── Rutas (/api/rutas) ──────────────────────────────────
                // AGENTE gestiona las rutas operativas
                // CLIENTE_NORMAL y CLIENTE_VIP pueden consultar rutas
                .requestMatchers(HttpMethod.POST,   "/api/rutas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.PUT,    "/api/rutas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/rutas/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/rutas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Cargas (/api/cargas) ────────────────────────────────
                // AGENTE y CLIENTE_VIP pueden crear y actualizar cargas
                // CLIENTE_NORMAL solo puede consultar sus cargas
                .requestMatchers(HttpMethod.POST, "/api/cargas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP")
                .requestMatchers(HttpMethod.PUT, "/api/cargas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP")
                .requestMatchers(HttpMethod.DELETE, "/api/cargas/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/cargas/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Servicios de resguardo (/api/servicios-resguardo) ───
                // ADMINISTRADOR y AGENTE pueden crear servicios
                // CLIENTE_VIP puede editar (PUT) su propio servicio
                // CLIENTE_NORMAL solo puede consultar
                .requestMatchers(HttpMethod.POST, "/api/servicios-resguardo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.PUT, "/api/servicios-resguardo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP")
                .requestMatchers(HttpMethod.DELETE, "/api/servicios-resguardo/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/servicios-resguardo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Zonas de riesgo (/api/zonas-riesgo) ────────────────
                // AGENTE consulta y gestiona zonas de riesgo
                .requestMatchers(HttpMethod.POST,   "/api/zonas-riesgo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.PUT,    "/api/zonas-riesgo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/zonas-riesgo/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/zonas-riesgo/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Incidentes (/api/incidentes) ────────────────────────
                // AGENTE reporta y consulta incidentes
                // CLIENTE_VIP y CLIENTE_NORMAL pueden consultar
                .requestMatchers(HttpMethod.POST, "/api/incidentes/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.PUT, "/api/incidentes/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/incidentes/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/incidentes/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Historial de servicios (/api/historiales) ───────────
                // AGENTE registra el historial
                // Todos los roles pueden consultar
                .requestMatchers(HttpMethod.POST, "/api/historiales/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE")
                .requestMatchers(HttpMethod.GET, "/api/historiales/**")
                    .hasAnyRole("ADMINISTRADOR", "AGENTE", "CLIENTE_VIP", "CLIENTE_NORMAL")

                // ── Cualquier otro endpoint requiere estar autenticado ───
                .anyRequest().authenticated()
            )

            // Registrar el filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
