package com.example.guardwatch.Repositories;

import com.example.guardwatch.Entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // ── Función derivada ──────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: filtra vehículos por estado (DISPONIBLE, EN_USO, EN_MANTENIMIENTO)
    List<Vehiculo> findByEstado(String estado);

    // ADMINISTRADOR: filtra por tipo (CAMIONETA, MOTOCICLETA, CAMION, etc.)
    List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);

    // ── JPQL ─────────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: vehículos con capacidad suficiente para una carga
    // El agente selecciona el vehículo correcto antes de iniciar el servicio
    @Query("SELECT v FROM Vehiculo v WHERE v.capacidadKg >= :capacidadMin")
    List<Vehiculo> buscarPorCapacidadMinima(@Param("capacidadMin") Double capacidadMin);

    // ADMINISTRADOR: vehículos disponibles de un tipo específico
    // Combinación que el admin usa al asignar recursos
    @Query("SELECT v FROM Vehiculo v WHERE v.tipoVehiculo = :tipo AND v.estado = 'DISPONIBLE'")
    List<Vehiculo> buscarDisponiblesPorTipo(@Param("tipo") String tipo);

    // ── SQL Nativo ────────────────────────────────────────────────────────────
    // AGENTE y ADMINISTRADOR: búsqueda exacta por placa
    @Query(value = "SELECT * FROM vehiculo WHERE placa = :placa", nativeQuery = true)
    Vehiculo buscarPorPlacaNativo(@Param("placa") String placa);

    // ADMINISTRADOR: vehículos más antiguos (control de mantenimiento preventivo)
    @Query(value = "SELECT * FROM vehiculo WHERE anio <= :anioMaximo ORDER BY anio ASC", nativeQuery = true)
    List<Vehiculo> buscarVehiculosAntiguosNativo(@Param("anioMaximo") Integer anioMaximo);
}
