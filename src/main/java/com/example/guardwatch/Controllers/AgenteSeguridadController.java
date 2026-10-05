package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.AgenteSeguridadService;
import com.example.guardwatch.dtos.AgenteSeguridadRequestDTO;
import com.example.guardwatch.dtos.AgenteSeguridadResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agentes")
public class AgenteSeguridadController {

    @Autowired private AgenteSeguridadService agenteService;

    // ADMINISTRADOR: registra nuevos agentes al sistema
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<AgenteSeguridadResponseDTO> registrar(@RequestBody AgenteSeguridadRequestDTO dto) {
        return ResponseEntity.ok(agenteService.registrar(dto));
    }

    // ADMINISTRADOR y AGENTE: consulta la lista completa de agentes
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agenteService.listarTodos());
    }

    // ADMINISTRADOR y AGENTE: consulta un agente por ID
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<AgenteSeguridadResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agenteService.buscarPorId(id));
    }

    // ADMINISTRADOR: actualiza datos de un agente
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<AgenteSeguridadResponseDTO> actualizar(@PathVariable Long id, @RequestBody AgenteSeguridadRequestDTO dto) {
        return ResponseEntity.ok(agenteService.actualizar(id, dto));
    }

    // ADMINISTRADOR: elimina un agente del sistema
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        agenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR y AGENTE: agentes activos y disponibles para asignación
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/disponibles")
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> buscarDisponibles(
            @RequestParam String estado, @RequestParam String disponibilidad) {
        return ResponseEntity.ok(agenteService.buscarPorEstadoYDisponibilidad(estado, disponibilidad));
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR: agentes por tipo de licencia (ARMAS, MOTOCICLETA, etc.)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-licencia")
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> buscarPorLicencia(@RequestParam String licencia) {
        return ResponseEntity.ok(agenteService.buscarPorLicencia(licencia));
    }

    // JPQL — ADMINISTRADOR y AGENTE: agentes con mínimo de experiencia requerida
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/experiencia-minima")
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> buscarPorExperiencia(@RequestParam Double minAnios) {
        return ResponseEntity.ok(agenteService.buscarPorExperienciaMinima(minAnios));
    }

    // JPQL — ADMINISTRADOR: reporte de cuántos agentes hay por estado
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/reporte-por-estado")
    public ResponseEntity<List<Object[]>> contarPorEstado() {
        return ResponseEntity.ok(agenteService.contarAgentesPorEstado());
    }

    // SQL NATIVO — ADMINISTRADOR y AGENTE: agentes por especialización
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/especializacion")
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> buscarPorEspecializacion(@RequestParam String especializacion) {
        return ResponseEntity.ok(agenteService.buscarPorEspecializacionNativo(especializacion));
    }

    // SQL NATIVO — ADMINISTRADOR: agentes libres hoy (sin servicio EN_CURSO)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/libres-hoy")
    public ResponseEntity<List<AgenteSeguridadResponseDTO>> agentesLibresHoy() {
        return ResponseEntity.ok(agenteService.listarAgentesLibresHoy());
    }
}
