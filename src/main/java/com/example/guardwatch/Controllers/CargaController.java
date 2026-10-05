package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.CargaService;
import com.example.guardwatch.dtos.CargaRequestDTO;
import com.example.guardwatch.dtos.CargaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargas")
public class CargaController {

    @Autowired private CargaService cargaService;

    // ADMINISTRADOR, AGENTE y CLIENTE_VIP: registran una carga nueva
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP')")
    @PostMapping
    public ResponseEntity<CargaResponseDTO> registrar(@RequestBody CargaRequestDTO dto) {
        return ResponseEntity.ok(cargaService.registrar(dto));
    }

    // ADMINISTRADOR y AGENTE: ven todas las cargas del sistema
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping
    public ResponseEntity<List<CargaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(cargaService.listarTodas());
    }

    // Todos los roles autorizados: buscan una carga por ID
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/{id}")
    public ResponseEntity<CargaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cargaService.buscarPorId(id));
    }

    // ADMINISTRADOR, AGENTE y CLIENTE_VIP: editan una carga (CLIENTE_VIP edita la suya)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP')")
    @PutMapping("/{id}")
    public ResponseEntity<CargaResponseDTO> actualizar(@PathVariable Long id, @RequestBody CargaRequestDTO dto) {
        return ResponseEntity.ok(cargaService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR puede eliminar cargas
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cargaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — Todos: cargas de un cliente específico
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-cliente/{clienteId}")
    public ResponseEntity<List<CargaResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cargaService.listarPorCliente(clienteId));
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR y AGENTE: cargas por estado (EN_TRANSITO, etc.)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-estado")
    public ResponseEntity<List<CargaResponseDTO>> listarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(cargaService.listarPorEstado(estado));
    }

    // JPQL — AGENTE y ADMINISTRADOR: cargas por nivel de riesgo y peso mínimo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-riesgo-y-peso")
    public ResponseEntity<List<CargaResponseDTO>> buscarPorRiesgoYPeso(
            @RequestParam String riesgo, @RequestParam Double pesoMin) {
        return ResponseEntity.ok(cargaService.buscarPorRiesgoYPesoMinimo(riesgo, pesoMin));
    }

    // JPQL — CLIENTE_VIP y ADMINISTRADOR: cargas de un cliente por nivel de riesgo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE_VIP')")
    @GetMapping("/por-cliente-y-riesgo")
    public ResponseEntity<List<CargaResponseDTO>> buscarPorClienteYRiesgo(
            @RequestParam Long clienteId, @RequestParam String riesgo) {
        return ResponseEntity.ok(cargaService.buscarPorClienteYRiesgo(clienteId, riesgo));
    }

    // SQL NATIVO — ADMINISTRADOR y CLIENTE_VIP: cargas de alto valor
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE_VIP')")
    @GetMapping("/alto-valor")
    public ResponseEntity<List<CargaResponseDTO>> buscarAltoValor(@RequestParam Double valorMin) {
        return ResponseEntity.ok(cargaService.buscarAltoValorNativo(valorMin));
    }

    // SQL NATIVO — ADMINISTRADOR: reporte de cargas por tipo
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/reporte-por-tipo")
    public ResponseEntity<List<Object[]>> contarPorTipo() {
        return ResponseEntity.ok(cargaService.contarCargasPorTipo());
    }
}
