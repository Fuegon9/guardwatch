package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.ServicioResguardoService;
import com.example.guardwatch.dtos.ServicioResguardoRequestDTO;
import com.example.guardwatch.dtos.ServicioResguardoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios-resguardo")
public class ServicioResguardoController {

    @Autowired private ServicioResguardoService servicioService;

    // ADMINISTRADOR y AGENTE crean nuevos servicios de resguardo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<ServicioResguardoResponseDTO> registrar(@RequestBody ServicioResguardoRequestDTO dto) {
        return ResponseEntity.ok(servicioService.registrar(dto));
    }

    // ADMINISTRADOR y AGENTE: lista completa de servicios
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping
    public ResponseEntity<List<ServicioResguardoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    // Todos los roles: consultar un servicio por ID
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResguardoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.buscarPorId(id));
    }

    // ADMINISTRADOR, AGENTE y CLIENTE_VIP: editar un servicio
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP')")
    @PutMapping("/{id}")
    public ResponseEntity<ServicioResguardoResponseDTO> actualizar(@PathVariable Long id, @RequestBody ServicioResguardoRequestDTO dto) {
        return ResponseEntity.ok(servicioService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR puede eliminar servicios
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — Todos: servicios filtrados por estado
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-estado")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(servicioService.buscarPorEstado(estado));
    }

    // FUNCIÓN DERIVADA — Todos: servicios propios de un cliente (CLIENTE solo ve los suyos)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-cliente/{clienteId}")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(servicioService.listarPorCliente(clienteId));
    }

    // JPQL — ADMINISTRADOR y AGENTE: servicios por cliente y prioridad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-cliente-y-prioridad")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> buscarPorClienteYPrioridad(
            @RequestParam Long clienteId, @RequestParam String prioridad) {
        return ResponseEntity.ok(servicioService.buscarPorClienteYPrioridad(clienteId, prioridad));
    }

    // JPQL — CLIENTE_VIP exclusivo: filtro avanzado por cliente + estado + prioridad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE_VIP')")
    @GetMapping("/filtro-avanzado")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> filtroAvanzado(
            @RequestParam Long clienteId, @RequestParam String estado, @RequestParam String prioridad) {
        return ResponseEntity.ok(servicioService.buscarPorClienteEstadoYPrioridad(clienteId, estado, prioridad));
    }

    // SQL NATIVO — ADMINISTRADOR y AGENTE: servicios que inician hoy
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/de-hoy")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> serviciosDeHoy() {
        return ResponseEntity.ok(servicioService.listarServiciosDeHoyNativo());
    }

    // SQL NATIVO — ADMINISTRADOR y AGENTE: servicios activos de alta prioridad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/criticos")
    public ResponseEntity<List<ServicioResguardoResponseDTO>> serviciosCriticos() {
        return ResponseEntity.ok(servicioService.listarServiciosCriticosNativo());
    }
}
