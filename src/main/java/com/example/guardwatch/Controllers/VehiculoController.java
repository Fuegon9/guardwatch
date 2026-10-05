package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.VehiculoService;
import com.example.guardwatch.dtos.VehiculoRequestDTO;
import com.example.guardwatch.dtos.VehiculoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired private VehiculoService vehiculoService;

    // ADMINISTRADOR y AGENTE: registran vehículos de la flota
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> registrar(@RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.registrar(dto));
    }

    // ADMINISTRADOR y AGENTE: lista completa de la flota
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    // ADMINISTRADOR y AGENTE: consulta un vehículo por ID
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    // ADMINISTRADOR y AGENTE: actualizar datos del vehículo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(@PathVariable Long id, @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR elimina vehículos de la flota
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — AGENTE y ADMINISTRADOR: vehículos por estado
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-estado")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(vehiculoService.buscarPorEstado(estado));
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR: flota filtrada por tipo de vehículo
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-tipo")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(vehiculoService.buscarPorTipo(tipo));
    }

    // JPQL — AGENTE y ADMINISTRADOR: vehículos con capacidad mínima para una carga
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/capacidad-minima")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorCapacidad(@RequestParam Double capacidadMin) {
        return ResponseEntity.ok(vehiculoService.buscarPorCapacidadMinima(capacidadMin));
    }

    // JPQL — AGENTE y ADMINISTRADOR: vehículos disponibles de un tipo específico
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/disponibles-por-tipo")
    public ResponseEntity<List<VehiculoResponseDTO>> disponiblesPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(vehiculoService.buscarDisponiblesPorTipo(tipo));
    }

    // SQL NATIVO — AGENTE y ADMINISTRADOR: búsqueda exacta por placa
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-placa")
    public ResponseEntity<VehiculoResponseDTO> buscarPorPlaca(@RequestParam String placa) {
        return ResponseEntity.ok(vehiculoService.buscarPorPlacaNativo(placa));
    }

    // SQL NATIVO — ADMINISTRADOR: vehículos antiguos para mantenimiento preventivo
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/antiguos")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarAntiguos(@RequestParam Integer anioMaximo) {
        return ResponseEntity.ok(vehiculoService.buscarVehiculosAntiguos(anioMaximo));
    }
}
