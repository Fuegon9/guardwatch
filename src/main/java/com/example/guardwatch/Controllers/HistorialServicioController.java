package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.HistorialServicioService;
import com.example.guardwatch.dtos.HistorialServicioRequestDTO;
import com.example.guardwatch.dtos.HistorialServicioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
public class HistorialServicioController {

    @Autowired private HistorialServicioService historialService;

    // AGENTE y ADMINISTRADOR: registran un cambio de estado en el historial
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<HistorialServicioResponseDTO> registrar(@RequestBody HistorialServicioRequestDTO dto) {
        return ResponseEntity.ok(historialService.registrar(dto));
    }

    // FUNCIÓN DERIVADA — Todos: historial de un servicio (más reciente primero)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-servicio/{servicioId}")
    public ResponseEntity<List<HistorialServicioResponseDTO>> listarPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(historialService.listarPorServicio(servicioId));
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR: todos los registros con un estado nuevo dado
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-estado-nuevo")
    public ResponseEntity<List<HistorialServicioResponseDTO>> listarPorEstadoNuevo(@RequestParam String estadoNuevo) {
        return ResponseEntity.ok(historialService.listarPorEstadoNuevo(estadoNuevo));
    }

    // JPQL — ADMINISTRADOR: cambios a un estado específico en todo el sistema
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/cambios-a-estado")
    public ResponseEntity<List<HistorialServicioResponseDTO>> buscarPorEstadoNuevo(@RequestParam String nuevoEstado) {
        return ResponseEntity.ok(historialService.buscarPorEstadoNuevo(nuevoEstado));
    }

    // JPQL — ADMINISTRADOR y AGENTE: busca transición específica en un servicio
    // Ej: de PENDIENTE a EN_CURSO — para auditoría de procesos
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/transicion")
    public ResponseEntity<List<HistorialServicioResponseDTO>> buscarTransicion(
            @RequestParam Long servicioId,
            @RequestParam String anterior,
            @RequestParam String nuevo) {
        return ResponseEntity.ok(historialService.buscarTransicionEspecifica(servicioId, anterior, nuevo));
    }

    // SQL NATIVO — Todos: historial completo de un servicio (cronológico)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/historial-nativo/{servicioId}")
    public ResponseEntity<List<HistorialServicioResponseDTO>> historialNativo(@PathVariable Long servicioId) {
        return ResponseEntity.ok(historialService.buscarHistorialNativo(servicioId));
    }

    // SQL NATIVO — ADMINISTRADOR: servicios con demasiados cambios de estado (detección de problemas)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/con-muchos-cambios")
    public ResponseEntity<List<Object[]>> conMuchosCambios(@RequestParam Long umbral) {
        return ResponseEntity.ok(historialService.serviciosConMasCambios(umbral));
    }
}
