package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Carga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CargaRepository extends JpaRepository<Carga, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // CLIENTE_VIP, CLIENTE_NORMAL, AGENTE, ADMINISTRADOR:
    // lista cargas de un cliente específico (cada cliente solo debería ver las suyas)
    List<Carga> findByClienteId(Long clienteId);

    // AGENTE y ADMINISTRADOR: cargas en tránsito pendientes de resguardo
    List<Carga> findByEstado(String estado);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: filtra por nivel de riesgo y peso mínimo
    // Ayuda a planificar cuántos agentes se necesitan para una carga
    @Query("SELECT c FROM Carga c WHERE c.nivelRiesgo = :riesgo AND c.pesoKg > :pesoMin")
    List<Carga> buscarPorRiesgoYPesoMinimo(@Param("riesgo") String riesgo, @Param("pesoMin") Double pesoMin);

    // CLIENTE_VIP y ADMINISTRADOR: cargas del cliente con un nivel de riesgo dado
    // El CLIENTE_VIP quiere saber qué cargas suyas son de alto riesgo
    @Query("SELECT c FROM Carga c WHERE c.cliente.id = :clienteId AND c.nivelRiesgo = :riesgo")
    List<Carga> buscarPorClienteYRiesgo(@Param("clienteId") Long clienteId, @Param("riesgo") String riesgo);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // ADMINISTRADOR y CLIENTE_VIP: cargas cuyo valor supera un umbral
    // CLIENTE_VIP puede ver si su carga de alto valor ya tiene resguardo asignado
    @Query(value = "SELECT * FROM carga WHERE valor_estimado >= :valorMin", nativeQuery = true)
    List<Carga> buscarCargasDeAltoValorNativo(@Param("valorMin") Double valorMin);

    // ADMINISTRADOR: reporte de cargas agrupadas por tipo
    @Query(value = "SELECT tipo_carga, COUNT(*) as total FROM carga GROUP BY tipo_carga ORDER BY total DESC", nativeQuery = true)
    List<Object[]> contarCargasPorTipoNativo();
}
