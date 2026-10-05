package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.*;
import com.example.guardwatch.Repositories.*;
import com.example.guardwatch.dtos.RutaRequestDTO;
import com.example.guardwatch.dtos.RutaResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RutaService {

    @Autowired private RutaRepository rutaRepo;
    @Autowired private ServicioResguardoRepository servicioRepo;
    @Autowired private ZonaRiesgoRepository zonaRepo;

    public RutaResponseDTO registrar(RutaRequestDTO dto) {
        Ruta r = new Ruta();
        r.setOrigen(dto.getOrigen()); r.setDestino(dto.getDestino());
        r.setDistanciaKm(dto.getDistanciaKm()); r.setTiempoEstimadoMinutos(dto.getTiempoEstimadoMinutos());
        r.setNivelRiesgo(dto.getNivelRiesgo()); r.setEstado(dto.getEstado());
        r.setCreatedAt(LocalDateTime.now()); r.setUpdatedAt(LocalDateTime.now());
        asignarRelaciones(r, dto);
        return mapearAResponse(rutaRepo.save(r));
    }

    public List<RutaResponseDTO> listarTodas() {
        return rutaRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public RutaResponseDTO buscarPorId(Long id) {
        return mapearAResponse(rutaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con ID: " + id)));
    }

    public RutaResponseDTO actualizar(Long id, RutaRequestDTO dto) {
        Ruta r = rutaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con ID: " + id));
        r.setOrigen(dto.getOrigen()); r.setDestino(dto.getDestino());
        r.setDistanciaKm(dto.getDistanciaKm()); r.setTiempoEstimadoMinutos(dto.getTiempoEstimadoMinutos());
        r.setNivelRiesgo(dto.getNivelRiesgo()); r.setEstado(dto.getEstado());
        r.setUpdatedAt(LocalDateTime.now());
        asignarRelaciones(r, dto);
        return mapearAResponse(rutaRepo.save(r));
    }

    public void eliminar(Long id) {
        if (!rutaRepo.existsById(id)) throw new ResourceNotFoundException("Ruta no encontrada con ID: " + id);
        rutaRepo.deleteById(id);
    }

    // Función derivada
    public RutaResponseDTO buscarPorServicioId(Long servicioId) {
        Ruta r = rutaRepo.findByServicioId(servicioId);
        if (r == null) throw new ResourceNotFoundException("Ruta no encontrada para servicio ID: " + servicioId);
        return mapearAResponse(r);
    }

    public List<RutaResponseDTO> buscarPorEstado(String estado) {
        return rutaRepo.findByEstado(estado).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<RutaResponseDTO> buscarPorOrigenYDestino(String origen, String destino) {
        return rutaRepo.buscarPorOrigenYDestino(origen, destino).stream().map(this::mapearAResponse).toList();
    }

    public List<RutaResponseDTO> buscarPorRangoDistancia(Double min, Double max) {
        return rutaRepo.buscarPorRangoDistancia(min, max).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<RutaResponseDTO> buscarPorRiesgoNativo(String riesgo) {
        return rutaRepo.buscarRutasPorRiesgoNativo(riesgo).stream().map(this::mapearAResponse).toList();
    }

    public List<RutaResponseDTO> listarRutasMasLargas(int limite) {
        return rutaRepo.listarRutasMasLargasNativo(limite).stream().map(this::mapearAResponse).toList();
    }

    private void asignarRelaciones(Ruta r, RutaRequestDTO dto) {
        if (dto.getIdServicio() != null) r.setServicio(servicioRepo.findById(dto.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + dto.getIdServicio())));
        if (dto.getIdsZonasRiesgo() != null) r.setZonasRiesgo(zonaRepo.findAllById(dto.getIdsZonasRiesgo()));
    }

    private RutaResponseDTO mapearAResponse(Ruta r) {
        RutaResponseDTO dto = new RutaResponseDTO();
        dto.setId(r.getId()); dto.setOrigen(r.getOrigen()); dto.setDestino(r.getDestino());
        dto.setDistanciaKm(r.getDistanciaKm()); dto.setTiempoEstimadoMinutos(r.getTiempoEstimadoMinutos());
        dto.setNivelRiesgo(r.getNivelRiesgo()); dto.setEstado(r.getEstado());
        if (r.getServicio()    != null) dto.setCodigoServicio(r.getServicio().getCodigoServicio());
        if (r.getZonasRiesgo() != null) dto.setNombresZonasRiesgo(r.getZonasRiesgo().stream().map(ZonaRiesgo::getNombre).toList());
        return dto;
    }
}
