package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: filtra incidentes por severidad (ALTA, MEDIA, BAJA)
    List<Incidente> findBySeveridad(String severidad);

    // AGENTE: ve los incidentes que él mismo registró (por agente_id)
    List<Incidente> findByAgenteId(Long agenteId);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // Todos los roles autorizados: incidentes de un servicio específico
    // CLIENTE_VIP y CLIENTE_NORMAL consultan incidentes de su servicio
    @Query("SELECT i FROM Incidente i WHERE i.servicio.id = :servicioId")
    List<Incidente> buscarPorServicioId(@Param("servicioId") Long servicioId);

    // ADMINISTRADOR: incidentes de alta severidad aún abiertos (dashboard de alertas)
    @Query("SELECT i FROM Incidente i WHERE i.severidad = :severidad AND i.estado = 'PENDIENTE'")
    List<Incidente> buscarCriticosPendientesPorSeveridad(@Param("severidad") String severidad);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: todos los incidentes pendientes de resolución
    @Query(value = "SELECT * FROM incidente WHERE estado = 'PENDIENTE'", nativeQuery = true)
    List<Incidente> listarIncidentesPendientesNativo();

    // ADMINISTRADOR: incidentes del día de hoy (reporte diario de novedades)
    @Query(value = "SELECT * FROM incidente WHERE DATE(fecha_hora) = CURRENT_DATE ORDER BY fecha_hora DESC", nativeQuery = true)
    List<Incidente> listarIncidentesDeHoyNativo();
}
