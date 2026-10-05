package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.SeguimientoService;
import com.example.guardwatch.dtos.SeguimientoRequestDTO;
import com.example.guardwatch.dtos.SeguimientoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

    @Autowired private SeguimientoService seguimientoService;

    // AGENTE: reporta su ubicación GPS durante el trayecto
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<SeguimientoResponseDTO> registrar(@RequestBody SeguimientoRequestDTO dto) {
        return ResponseEntity.ok(seguimientoService.registrar(dto));
    }

    // FUNCIÓN DERIVADA — Todos: historial de posiciones de un servicio (más reciente primero)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-servicio/{servicioId}")
    public ResponseEntity<List<SeguimientoResponseDTO>> listarPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(seguimientoService.listarPorServicio(servicioId));
    }

    // FUNCIÓN DERIVADA — AGENTE y ADMINISTRADOR: posiciones de un vehículo específico
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-vehiculo/{vehiculoId}")
    public ResponseEntity<List<SeguimientoResponseDTO>> listarPorVehiculo(@PathVariable Long vehiculoId) {
        return ResponseEntity.ok(seguimientoService.listarPorVehiculo(vehiculoId));
    }

    // JPQL — ADMINISTRADOR y AGENTE: detecta excesos de velocidad (alerta de seguridad)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/excesos-velocidad")
    public ResponseEntity<List<SeguimientoResponseDTO>> excesosVelocidad(@RequestParam Double limiteKmh) {
        return ResponseEntity.ok(seguimientoService.buscarExcesosVelocidad(limiteKmh));
    }

    // JPQL — CLIENTE_VIP y ADMINISTRADOR: recorrido completo de un servicio (cronológico)
    // CLIENTE_VIP puede ver la trayectoria en tiempo real de su carga
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP')")
    @GetMapping("/recorrido/{servicioId}")
    public ResponseEntity<List<SeguimientoResponseDTO>> recorridoServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(seguimientoService.buscarRecorridoServicio(servicioId));
    }

    // SQL NATIVO — AGENTE y ADMINISTRADOR: última ubicación conocida de un vehículo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/ultima-ubicacion/{vehiculoId}")
    public ResponseEntity<SeguimientoResponseDTO> ultimaUbicacion(@PathVariable Long vehiculoId) {
        return ResponseEntity.ok(seguimientoService.buscarUltimaUbicacionVehiculo(vehiculoId));
    }

    // SQL NATIVO — ADMINISTRADOR: cuántos reportes GPS tiene cada servicio
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/reportes-por-servicio")
    public ResponseEntity<List<Object[]>> reportesPorServicio() {
        return ResponseEntity.ok(seguimientoService.contarReportesPorServicio());
    }
}
