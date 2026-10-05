package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // ADMINISTRADOR: busca cliente por RUC para verificar si ya existe
    Optional<Cliente> findByRuc(String ruc);

    // ADMINISTRADOR: busca clientes por estado (ACTIVO/INACTIVO)
    List<Cliente> findByEstado(String estado);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // ADMINISTRADOR: búsqueda parcial de razón social para autocompletar formularios
    @Query("SELECT c FROM Cliente c WHERE c.razonSocial LIKE %:nombre%")
    List<Cliente> buscarPorRazonSocial(@Param("nombre") String nombre);

    // ADMINISTRADOR: clientes que tienen al menos un servicio en curso
    // Útil para el panel de monitoreo
    @Query("""
        SELECT DISTINCT c FROM Cliente c
        INNER JOIN ServicioResguardo s ON s.cliente.id = c.id
        WHERE s.estado = :estadoServicio
    """)
    List<Cliente> buscarClientesConServicioEnEstado(@Param("estadoServicio") String estadoServicio);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // ADMINISTRADOR: lista solo los clientes activos (panel principal)
    @Query(value = "SELECT * FROM cliente WHERE estado = 'ACTIVO'", nativeQuery = true)
    List<Cliente> listarClientesActivosNativo();

    // ADMINISTRADOR: clientes ordenados por cantidad de servicios (más activos primero)
    @Query(value = """
        SELECT c.* FROM cliente c
        LEFT JOIN servicio_resguardo sr ON sr.cliente_id = c.id
        GROUP BY c.id
        ORDER BY COUNT(sr.id) DESC
    """, nativeQuery = true)
    List<Cliente> listarClientesPorActividadNativo();
}
