package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.ClienteService;
import com.example.guardwatch.dtos.ClienteRequestDTO;
import com.example.guardwatch.dtos.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired private ClienteService clienteService;

    // Solo ADMINISTRADOR gestiona clientes
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.registrar(dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR: busca cliente por RUC
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-ruc")
    public ResponseEntity<ClienteResponseDTO> buscarPorRuc(@RequestParam String ruc) {
        return ResponseEntity.ok(clienteService.buscarPorRuc(ruc));
    }

    // FUNCIÓN DERIVADA — ADMINISTRADOR: filtra por estado
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-estado")
    public ResponseEntity<List<ClienteResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(clienteService.buscarPorEstado(estado));
    }

    // JPQL — ADMINISTRADOR: búsqueda por nombre parcial
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteResponseDTO>> buscarPorRazonSocial(@RequestParam String nombre) {
        return ResponseEntity.ok(clienteService.buscarPorRazonSocial(nombre));
    }

    // JPQL — ADMINISTRADOR: clientes con servicios en un estado dado
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/con-servicio-en-estado")
    public ResponseEntity<List<ClienteResponseDTO>> conServicioEnEstado(@RequestParam String estadoServicio) {
        return ResponseEntity.ok(clienteService.buscarClientesConServicioEnEstado(estadoServicio));
    }

    // SQL NATIVO — ADMINISTRADOR: solo clientes activos
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/activos")
    public ResponseEntity<List<ClienteResponseDTO>> listarActivos() {
        return ResponseEntity.ok(clienteService.listarActivosNativo());
    }

    // SQL NATIVO — ADMINISTRADOR: clientes ordenados por actividad
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/por-actividad")
    public ResponseEntity<List<ClienteResponseDTO>> listarPorActividad() {
        return ResponseEntity.ok(clienteService.listarPorActividadNativo());
    }
}
