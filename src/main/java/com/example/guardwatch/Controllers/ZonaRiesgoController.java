package com.example.guardwatch.Controllers;

import com.example.guardwatch.Services.ZonaRiesgoService;
import com.example.guardwatch.dtos.ZonaRiesgoRequestDTO;
import com.example.guardwatch.dtos.ZonaRiesgoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zonas-riesgo")
public class ZonaRiesgoController {

    @Autowired private ZonaRiesgoService zonaService;

    // ADMINISTRADOR y AGENTE: registran zonas de riesgo identificadas en campo
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PostMapping
    public ResponseEntity<ZonaRiesgoResponseDTO> registrar(@RequestBody ZonaRiesgoRequestDTO dto) {
        return ResponseEntity.ok(zonaService.registrar(dto));
    }

    // Todos los roles: consultan las zonas de riesgo activas
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(zonaService.listarTodas());
    }

    // Todos los roles: consultar una zona por ID
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/{id}")
    public ResponseEntity<ZonaRiesgoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(zonaService.buscarPorId(id));
    }

    // ADMINISTRADOR y AGENTE: actualizan datos de una zona
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ZonaRiesgoResponseDTO> actualizar(@PathVariable Long id, @RequestBody ZonaRiesgoRequestDTO dto) {
        return ResponseEntity.ok(zonaService.actualizar(id, dto));
    }

    // Solo ADMINISTRADOR puede eliminar zonas
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        zonaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // FUNCIÓN DERIVADA — Todos: zonas filtradas por nivel de peligrosidad
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-nivel-riesgo")
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> buscarPorNivel(@RequestParam String nivelRiesgo) {
        return ResponseEntity.ok(zonaService.buscarPorNivelRiesgo(nivelRiesgo));
    }

    // FUNCIÓN DERIVADA — AGENTE y ADMINISTRADOR: zonas activas en una provincia
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-provincia-y-estado")
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> buscarPorProvinciaYEstado(
            @RequestParam String provincia, @RequestParam String estado) {
        return ResponseEntity.ok(zonaService.buscarPorProvinciaYEstado(provincia, estado));
    }

    // JPQL — AGENTE y ADMINISTRADOR: zonas en un distrito (verificación antes de entrar a ruta)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/por-distrito")
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> buscarPorDistrito(@RequestParam String distrito) {
        return ResponseEntity.ok(zonaService.buscarPorDistrito(distrito));
    }

    // JPQL — AGENTE y ADMINISTRADOR: zonas CRÍTICAS y ALTAS activas (alerta antes de salir)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE')")
    @GetMapping("/peligrosas-activas")
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> peligrosasActivas() {
        return ResponseEntity.ok(zonaService.listarZonasPeligrosasActivas());
    }

    // SQL NATIVO — Todos: zonas de un departamento
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','AGENTE','CLIENTE_VIP','CLIENTE_NORMAL')")
    @GetMapping("/por-departamento")
    public ResponseEntity<List<ZonaRiesgoResponseDTO>> buscarPorDepartamento(@RequestParam String departamento) {
        return ResponseEntity.ok(zonaService.buscarPorDepartamentoNativo(departamento));
    }

    // SQL NATIVO — ADMINISTRADOR: resumen de zonas activas por nivel (dashboard)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/resumen-por-nivel")
    public ResponseEntity<List<Object[]>> resumenPorNivel() {
        return ResponseEntity.ok(zonaService.resumenZonasPorNivel());
    }
}
