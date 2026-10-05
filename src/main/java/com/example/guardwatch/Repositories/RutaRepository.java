package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: busca la ruta de un servicio específico
    Ruta findByServicioId(Long servicioId);

    // Todos los roles: filtra rutas por estado (ACTIVA, INACTIVA, EN_REVISION)
    List<Ruta> findByEstado(String estado);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // AGENTE, CLIENTE_VIP, CLIENTE_NORMAL: busca rutas por tramo origen-destino
    @Query("SELECT r FROM Ruta r WHERE r.origen = :origen AND r.destino = :destino")
    List<Ruta> buscarPorOrigenYDestino(@Param("origen") String origen, @Param("destino") String destino);

    // AGENTE y ADMINISTRADOR: rutas con distancia dentro de un rango operativo
    // Útil para planificar combustible y tiempo de turno del agente
    @Query("SELECT r FROM Ruta r WHERE r.distanciaKm BETWEEN :min AND :max ORDER BY r.distanciaKm ASC")
    List<Ruta> buscarPorRangoDistancia(@Param("min") Double min, @Param("max") Double max);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // Todos los roles: rutas filtradas por nivel de riesgo
    // CLIENTE_VIP y CLIENTE_NORMAL ven el riesgo de sus rutas
    @Query(value = "SELECT * FROM ruta WHERE nivel_riesgo = :riesgo", nativeQuery = true)
    List<Ruta> buscarRutasPorRiesgoNativo(@Param("riesgo") String riesgo);

    // ADMINISTRADOR: las N rutas más largas (planificación de recursos)
    @Query(value = "SELECT * FROM ruta ORDER BY distancia_km DESC LIMIT :limite", nativeQuery = true)
    List<Ruta> listarRutasMasLargasNativo(@Param("limite") int limite);
}
