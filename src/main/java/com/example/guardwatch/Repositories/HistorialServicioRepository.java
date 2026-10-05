package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.HistorialServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistorialServicioRepository extends JpaRepository<HistorialServicio, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // Todos los roles autorizados: historial de un servicio ordenado más reciente primero
    List<HistorialServicio> findByServicioIdOrderByFechaHoraDesc(Long servicioId);

    // ADMINISTRADOR: todos los cambios al estado "CANCELADO" para auditoría
    List<HistorialServicio> findByEstadoNuevo(String estadoNuevo);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: historial filtrado por el nuevo estado
    @Query("SELECT h FROM HistorialServicio h WHERE h.estadoNuevo = :nuevoEstado")
    List<HistorialServicio> buscarPorEstadoNuevo(@Param("nuevoEstado") String nuevoEstado);

    // ADMINISTRADOR: cambios de estado hechos en un rango de fechas
    // Se usa con Object[] para no agregar dependencias de fecha extras
    @Query("""
        SELECT h FROM HistorialServicio h
        WHERE h.servicio.id = :servicioId
          AND h.estadoAnterior = :anterior
          AND h.estadoNuevo   = :nuevo
    """)
    List<HistorialServicio> buscarTransicionEspecifica(
        @Param("servicioId") Long servicioId,
        @Param("anterior") String anterior,
        @Param("nuevo") String nuevo
    );

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // ADMINISTRADOR: historial completo de un servicio en orden cronológico
    @Query(value = "SELECT * FROM historial_servicio WHERE servicio_id = :servicioId", nativeQuery = true)
    List<HistorialServicio> buscarHistorialPorServicioNativo(@Param("servicioId") Long servicioId);

    // ADMINISTRADOR: cuántos cambios de estado ha tenido cada servicio (detección de problemas)
    @Query(value = """
        SELECT servicio_id, COUNT(*) as cambios
        FROM historial_servicio
        GROUP BY servicio_id
        HAVING COUNT(*) > :umbral
        ORDER BY cambios DESC
    """, nativeQuery = true)
    List<Object[]> serviciosConMasCambiosNativo(@Param("umbral") Long umbral);
}
