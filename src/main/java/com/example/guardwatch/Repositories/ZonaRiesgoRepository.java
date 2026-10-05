package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.ZonaRiesgo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZonaRiesgoRepository extends JpaRepository<ZonaRiesgo, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // Todos los roles: busca zonas por nivel de peligrosidad
    List<ZonaRiesgo> findByNivelRiesgo(String nivelRiesgo);

    // AGENTE y ADMINISTRADOR: zonas activas en una provincia específica
    List<ZonaRiesgo> findByProvinciaAndEstado(String provincia, String estado);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: zonas en un distrito dado (verificación de ruta)
    @Query("SELECT z FROM ZonaRiesgo z WHERE z.distrito = :distrito")
    List<ZonaRiesgo> buscarPorDistrito(@Param("distrito") String distrito);

    // AGENTE: zonas CRÍTICAS o ALTA activas para alertar antes de ingresar
    @Query("SELECT z FROM ZonaRiesgo z WHERE z.nivelRiesgo IN ('CRITICO','ALTO') AND z.estado = 'ACTIVO'")
    List<ZonaRiesgo> listarZonasPeligrosasActivas();

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // Todos los roles: zonas agrupadas por departamento
    @Query(value = "SELECT * FROM zona_riesgo WHERE departamento = :depa", nativeQuery = true)
    List<ZonaRiesgo> buscarPorDepartamentoNativo(@Param("depa") String depa);

    // ADMINISTRADOR: resumen de zonas activas por nivel de riesgo (dashboard)
    @Query(value = """
        SELECT nivel_riesgo, COUNT(*) as total
        FROM zona_riesgo
        WHERE estado = 'ACTIVO'
        GROUP BY nivel_riesgo
        ORDER BY total DESC
    """, nativeQuery = true)
    List<Object[]> resumenZonasPorNivelNativo();
}
