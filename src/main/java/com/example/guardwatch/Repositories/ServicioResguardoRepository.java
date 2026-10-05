package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.ServicioResguardo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioResguardoRepository extends JpaRepository<ServicioResguardo, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // Todos los roles pueden filtrar por estado (PENDIENTE, EN_CURSO, FINALIZADO, CANCELADO)
    List<ServicioResguardo> findByEstado(String estado);

    // CLIENTE_VIP y CLIENTE_NORMAL: lista servicios de su propio cliente
    // Es la petición más importante para clientes: ver sus propios servicios
    List<ServicioResguardo> findByClienteId(Long clienteId);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // ADMINISTRADOR y AGENTE: servicios por cliente y prioridad (ALTA, MEDIA, BAJA)
    // Permite priorizar qué servicios atender primero
    @Query("SELECT s FROM ServicioResguardo s WHERE s.cliente.id = :clienteId AND s.prioridad = :prioridad")
    List<ServicioResguardo> buscarPorClienteYPrioridad(@Param("clienteId") Long clienteId, @Param("prioridad") String prioridad);

    // CLIENTE_VIP: puede ver sus servicios por estado y prioridad combinados
    // Esto diferencia al CLIENTE_VIP del CLIENTE_NORMAL (más filtros disponibles)
    @Query("""
        SELECT s FROM ServicioResguardo s
        WHERE s.cliente.id = :clienteId
          AND s.estado = :estado
          AND s.prioridad = :prioridad
    """)
    List<ServicioResguardo> buscarPorClienteEstadoYPrioridad(
        @Param("clienteId") Long clienteId,
        @Param("estado") String estado,
        @Param("prioridad") String prioridad
    );

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // ADMINISTRADOR y AGENTE: servicios que inician hoy (panel operativo)
    @Query(value = "SELECT * FROM servicio_resguardo WHERE DATE(fecha_inicio) = CURRENT_DATE", nativeQuery = true)
    List<ServicioResguardo> listarServiciosDeHoyNativo();

    // ADMINISTRADOR: servicios activos con prioridad ALTA (alerta de operaciones críticas)
    @Query(value = """
        SELECT * FROM servicio_resguardo
        WHERE estado = 'EN_CURSO' AND prioridad = 'ALTA'
        ORDER BY fecha_inicio ASC
    """, nativeQuery = true)
    List<ServicioResguardo> listarServiciosCriticosNativo();
}
