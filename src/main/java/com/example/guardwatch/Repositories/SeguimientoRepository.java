package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // Todos los roles autorizados: historial GPS de un servicio (orden cronológico inverso)
    List<Seguimiento> findByServicioIdOrderByFechaHoraDesc(Long servicioId);

    // AGENTE y ADMINISTRADOR: puntos de seguimiento de un vehículo específico
    List<Seguimiento> findByVehiculoId(Long vehiculoId);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // ADMINISTRADOR y AGENTE: detecta excesos de velocidad para alertas automáticas
    @Query("SELECT s FROM Seguimiento s WHERE s.velocidadKmh > :limiteVelocidad")
    List<Seguimiento> buscarExcesosVelocidad(@Param("limiteVelocidad") Double limiteVelocidad);

    // CLIENTE_VIP: recorrido de un servicio ordenado cronológicamente
    // El CLIENTE_VIP puede ver dónde está su carga en tiempo real
    @Query("SELECT s FROM Seguimiento s WHERE s.servicio.id = :servicioId ORDER BY s.fechaHora ASC")
    List<Seguimiento> buscarRecorridoServicio(@Param("servicioId") Long servicioId);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: última ubicación conocida de un vehículo
    @Query(value = "SELECT * FROM seguimiento WHERE vehiculo_id = :vehiculoId ORDER BY fecha_hora DESC LIMIT 1", nativeQuery = true)
    Seguimiento buscarUltimaUbicacionVehiculoNativo(@Param("vehiculoId") Long vehiculoId);

    // ADMINISTRADOR: cantidad de reportes GPS por servicio (control de calidad del agente)
    @Query(value = """
        SELECT servicio_id, COUNT(*) as total_reportes
        FROM seguimiento
        GROUP BY servicio_id
        ORDER BY total_reportes DESC
    """, nativeQuery = true)
    List<Object[]> contarReportesPorServicioNativo();
}
