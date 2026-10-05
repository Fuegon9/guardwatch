package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.AgenteSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgenteSeguridadRepository extends JpaRepository<AgenteSeguridad, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // Busca agentes activos y disponibles → AGENTE y ADMINISTRADOR lo usan
    // para asignar agentes antes de iniciar un servicio
    List<AgenteSeguridad> findByEstadoAndDisponibilidad(String estado, String disponibilidad);

    // Busca agentes por licencia específica (ej: "ARMAS", "MOTOCICLETA")
    // Solo ADMINISTRADOR y AGENTE la usan para verificar habilitaciones
    List<AgenteSeguridad> findByLicencia(String licencia);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // ADMINISTRADOR y AGENTE: filtra agentes con experiencia mínima requerida
    // Útil al asignar misiones de alta prioridad
    @Query("SELECT a FROM AgenteSeguridad a WHERE a.experienciaAnios >= :minExperiencia")
    List<AgenteSeguridad> buscarPorExperienciaMinima(@Param("minExperiencia") Double minExperiencia);

    // ADMINISTRADOR: cuenta cuántos agentes hay por estado (reporte de plantilla)
    @Query("SELECT a.estado, COUNT(a) FROM AgenteSeguridad a GROUP BY a.estado")
    List<Object[]> contarAgentesPorEstado();

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // ADMINISTRADOR y AGENTE: busca por especialización (ej: "CUSTODIA_VALORES")
    @Query(value = "SELECT * FROM agente_seguridad WHERE especializacion = :especializacion", nativeQuery = true)
    List<AgenteSeguridad> buscarPorEspecializacionNativo(@Param("especializacion") String especializacion);

    // ADMINISTRADOR: lista agentes sin servicio activo (disponibles hoy)
    // JOIN negativo contra servicio_agente para encontrar libres
    @Query(value = """
        SELECT a.* FROM agente_seguridad a
        WHERE a.disponibilidad = 'DISPONIBLE'
          AND a.estado = 'ACTIVO'
          AND a.id NOT IN (
              SELECT sa.agente_id FROM servicio_agente sa
              INNER JOIN servicio_resguardo sr ON sr.id = sa.servicio_id
              WHERE sr.estado = 'EN_CURSO'
          )
    """, nativeQuery = true)
    List<AgenteSeguridad> listarAgentesLibresHoyNativo();
}
