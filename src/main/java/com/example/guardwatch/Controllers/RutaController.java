package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.RutaService;
import com.example.guardwatch.dtos.RutaRequestDTO;
import com.example.guardwatch.dtos.RutaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired private RutaService rutaService;

    // ADMINISTRADOR y AGENTE: crean rutas operativas
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<RutaResponseDTO> registrar(@RequestBody RutaRequestDTO dto) {
        return ResponseEntity.ok(rutaService.registrar(dto));
    }

    // Todos los roles: ver todas las rutas disponibles
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping
    public ResponseEntity<List<RutaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(rutaService.listarTodas());
    }

    // Todos los roles: ver una ruta específica
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/{id}")
    public ResponseEntity<RutaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.buscarPorId(id));
    }

    // ADMINISTRADOR y AGENTE: actualizar una ruta
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<RutaResponseDTO> actualizar(@PathVariable Long id, @RequestBody RutaRequestDTO dto) {
        return ResponseEntity.ok(rutaService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR elimina rutas
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — AGENTE y ADMINISTRADOR: ruta de un servicio específico
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-servicio/{servicioId}")
    public ResponseEntity<RutaResponseDTO> buscarPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(rutaService.buscarPorServicioId(servicioId));
    }

    // FUNCIÓN DERIVADA — Todos: rutas por estado
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-estado")
    public ResponseEntity<List<RutaResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(rutaService.buscarPorEstado(estado));
    }

    // JPQL — Todos: busca rutas por tramo origen-destino
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-tramo")
    public ResponseEntity<List<RutaResponseDTO>> buscarPorTramo(@RequestParam String origen, @RequestParam String destino) {
        return ResponseEntity.ok(rutaService.buscarPorOrigenYDestino(origen, destino));
    }

    // JPQL — AGENTE y ADMINISTRADOR: rutas dentro de un rango de distancia
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-rango-distancia")
    public ResponseEntity<List<RutaResponseDTO>> buscarPorRangoDistancia(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(rutaService.buscarPorRangoDistancia(min, max));
    }

    // SQL NATIVO — Todos: rutas por nivel de riesgo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-riesgo")
    public ResponseEntity<List<RutaResponseDTO>> buscarPorRiesgo(@RequestParam String riesgo) {
        return ResponseEntity.ok(rutaService.buscarPorRiesgoNativo(riesgo));
    }

    // SQL NATIVO — ADMINISTRADOR: las N rutas más largas
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/mas-largas")
    public ResponseEntity<List<RutaResponseDTO>> rutasMasLargas(@RequestParam int limite) {
        return ResponseEntity.ok(rutaService.listarRutasMasLargas(limite));
    }
}
