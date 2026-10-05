package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.IncidenteService;
import com.example.guardwatch.dtos.IncidenteRequestDTO;
import com.example.guardwatch.dtos.IncidenteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidentes")
public class IncidenteController {

    @Autowired private IncidenteService incidenteService;

    // AGENTE y ADMINISTRADOR: reportan un nuevo incidente en campo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<IncidenteResponseDTO> registrar(@RequestBody IncidenteRequestDTO dto) {
        return ResponseEntity.ok(incidenteService.registrar(dto));
    }

    // ADMINISTRADOR y AGENTE: lista todos los incidentes
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping
    public ResponseEntity<List<IncidenteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(incidenteService.listarTodos());
    }

    // Todos los roles autorizados: consultar un incidente específico
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/{id}")
    public ResponseEntity<IncidenteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(incidenteService.buscarPorId(id));
    }

    // ADMINISTRADOR y AGENTE: actualizar el estado de un incidente
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<IncidenteResponseDTO> actualizar(@PathVariable Long id, @RequestBody IncidenteRequestDTO dto) {
        return ResponseEntity.ok(incidenteService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR puede eliminar incidentes
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        incidenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — AGENTE y ADMINISTRADOR: filtrar por severidad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-severidad")
    public ResponseEntity<List<IncidenteResponseDTO>> buscarPorSeveridad(@RequestParam String severidad) {
        return ResponseEntity.ok(incidenteService.buscarPorSeveridad(severidad));
    }

    // FUNCIÓN DERIVADA — AGENTE: ver los incidentes que él reportó
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-agente/{agenteId}")
    public ResponseEntity<List<IncidenteResponseDTO>> buscarPorAgente(@PathVariable Long agenteId) {
        return ResponseEntity.ok(incidenteService.buscarPorAgente(agenteId));
    }

    // JPQL — Todos los roles: incidentes de un servicio (clientes ven los de su servicio)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-servicio/{servicioId}")
    public ResponseEntity<List<IncidenteResponseDTO>> buscarPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(incidenteService.buscarPorServicioId(servicioId));
    }

    // JPQL — ADMINISTRADOR: incidentes críticos pendientes por severidad (alertas)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/criticos-pendientes")
    public ResponseEntity<List<IncidenteResponseDTO>> criticosPendientes(@RequestParam String severidad) {
        return ResponseEntity.ok(incidenteService.buscarCriticosPendientes(severidad));
    }

    // SQL NATIVO — AGENTE y ADMINISTRADOR: incidentes pendientes de resolución
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/pendientes")
    public ResponseEntity<List<IncidenteResponseDTO>> pendientes() {
        return ResponseEntity.ok(incidenteService.listarPendientesNativo());
    }

    // SQL NATIVO — ADMINISTRADOR: novedades del día de hoy
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/de-hoy")
    public ResponseEntity<List<IncidenteResponseDTO>> incidentesDeHoy() {
        return ResponseEntity.ok(incidenteService.listarIncidentesDeHoy());
    }
}
