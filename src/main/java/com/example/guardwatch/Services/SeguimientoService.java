package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.*;
import com.example.guardwatch.Repositories.*;
import com.example.guardwatch.dtos.SeguimientoRequestDTO;
import com.example.guardwatch.dtos.SeguimientoResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoService {

    @Autowired private SeguimientoRepository seguimientoRepo;
    @Autowired private ServicioResguardoRepository servicioRepo;
    @Autowired private VehiculoRepository vehiculoRepo;

    public SeguimientoResponseDTO registrar(SeguimientoRequestDTO dto) {
        Seguimiento s = new Seguimiento();
        s.setLatitud(dto.getLatitud()); s.setLongitud(dto.getLongitud());
        s.setVelocidadKmh(dto.getVelocidadKmh()); s.setEstado(dto.getEstado());
        s.setFechaHora(dto.getFechaHora());
        if (dto.getIdServicio() != null) s.setServicio(servicioRepo.findById(dto.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + dto.getIdServicio())));
        if (dto.getIdVehiculo() != null) s.setVehiculo(vehiculoRepo.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + dto.getIdVehiculo())));
        return mapearAResponse(seguimientoRepo.save(s));
    }

    // Función derivada
    public List<SeguimientoResponseDTO> listarPorServicio(Long servicioId) {
        return seguimientoRepo.findByServicioIdOrderByFechaHoraDesc(servicioId).stream().map(this::mapearAResponse).toList();
    }

    public List<SeguimientoResponseDTO> listarPorVehiculo(Long vehiculoId) {
        return seguimientoRepo.findByVehiculoId(vehiculoId).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<SeguimientoResponseDTO> buscarExcesosVelocidad(Double limiteVelocidad) {
        return seguimientoRepo.buscarExcesosVelocidad(limiteVelocidad).stream().map(this::mapearAResponse).toList();
    }

    public List<SeguimientoResponseDTO> buscarRecorridoServicio(Long servicioId) {
        return seguimientoRepo.buscarRecorridoServicio(servicioId).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public SeguimientoResponseDTO buscarUltimaUbicacionVehiculo(Long vehiculoId) {
        Seguimiento s = seguimientoRepo.buscarUltimaUbicacionVehiculoNativo(vehiculoId);
        if (s == null) throw new ResourceNotFoundException("Sin registro de ubicación para vehículo ID: " + vehiculoId);
        return mapearAResponse(s);
    }

    public List<Object[]> contarReportesPorServicio() {
        return seguimientoRepo.contarReportesPorServicioNativo();
    }

    private SeguimientoResponseDTO mapearAResponse(Seguimiento s) {
        SeguimientoResponseDTO dto = new SeguimientoResponseDTO();
        dto.setId(s.getId()); dto.setLatitud(s.getLatitud()); dto.setLongitud(s.getLongitud());
        dto.setVelocidadKmh(s.getVelocidadKmh()); dto.setEstado(s.getEstado());
        dto.setFechaHora(s.getFechaHora());
        if (s.getServicio() != null) dto.setCodigoServicio(s.getServicio().getCodigoServicio());
        if (s.getVehiculo() != null) dto.setPlacaVehiculo(s.getVehiculo().getPlaca());
        return dto;
    }
}
