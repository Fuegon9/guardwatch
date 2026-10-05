package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.*;
import com.example.guardwatch.Repositories.*;
import com.example.guardwatch.dtos.IncidenteRequestDTO;
import com.example.guardwatch.dtos.IncidenteResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenteService {

    @Autowired private IncidenteRepository incidenteRepo;
    @Autowired private ServicioResguardoRepository servicioRepo;
    @Autowired private AgenteSeguridadRepository agenteRepo;
    @Autowired private ZonaRiesgoRepository zonaRepo;

    public IncidenteResponseDTO registrar(IncidenteRequestDTO dto) {
        Incidente i = new Incidente();
        i.setTipoIncidente(dto.getTipoIncidente()); i.setDescripcion(dto.getDescripcion());
        i.setSeveridad(dto.getSeveridad()); i.setLatitud(dto.getLatitud());
        i.setLongitud(dto.getLongitud()); i.setEstado(dto.getEstado());
        i.setFechaHora(dto.getFechaHora()); i.setCreatedAt(LocalDateTime.now());
        asignarRelaciones(i, dto);
        return mapearAResponse(incidenteRepo.save(i));
    }

    public List<IncidenteResponseDTO> listarTodos() {
        return incidenteRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public IncidenteResponseDTO buscarPorId(Long id) {
        return mapearAResponse(incidenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidente no encontrado con ID: " + id)));
    }

    public IncidenteResponseDTO actualizar(Long id, IncidenteRequestDTO dto) {
        Incidente i = incidenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidente no encontrado con ID: " + id));
        i.setTipoIncidente(dto.getTipoIncidente()); i.setDescripcion(dto.getDescripcion());
        i.setSeveridad(dto.getSeveridad()); i.setLatitud(dto.getLatitud());
        i.setLongitud(dto.getLongitud()); i.setEstado(dto.getEstado());
        i.setFechaHora(dto.getFechaHora());
        asignarRelaciones(i, dto);
        return mapearAResponse(incidenteRepo.save(i));
    }

    public void eliminar(Long id) {
        if (!incidenteRepo.existsById(id)) throw new ResourceNotFoundException("Incidente no encontrado con ID: " + id);
        incidenteRepo.deleteById(id);
    }

    // Función derivada
    public List<IncidenteResponseDTO> buscarPorSeveridad(String severidad) {
        return incidenteRepo.findBySeveridad(severidad).stream().map(this::mapearAResponse).toList();
    }

    public List<IncidenteResponseDTO> buscarPorAgente(Long agenteId) {
        return incidenteRepo.findByAgenteId(agenteId).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<IncidenteResponseDTO> buscarPorServicioId(Long servicioId) {
        return incidenteRepo.buscarPorServicioId(servicioId).stream().map(this::mapearAResponse).toList();
    }

    public List<IncidenteResponseDTO> buscarCriticosPendientes(String severidad) {
        return incidenteRepo.buscarCriticosPendientesPorSeveridad(severidad).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<IncidenteResponseDTO> listarPendientesNativo() {
        return incidenteRepo.listarIncidentesPendientesNativo().stream().map(this::mapearAResponse).toList();
    }

    public List<IncidenteResponseDTO> listarIncidentesDeHoy() {
        return incidenteRepo.listarIncidentesDeHoyNativo().stream().map(this::mapearAResponse).toList();
    }

    private void asignarRelaciones(Incidente i, IncidenteRequestDTO dto) {
        if (dto.getIdServicio() != null) i.setServicio(servicioRepo.findById(dto.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + dto.getIdServicio())));
        if (dto.getIdAgente() != null) i.setAgente(agenteRepo.findById(dto.getIdAgente())
                .orElseThrow(() -> new ResourceNotFoundException("Agente no encontrado con ID: " + dto.getIdAgente())));
        if (dto.getIdZonaRiesgo() != null) i.setZonaRiesgo(zonaRepo.findById(dto.getIdZonaRiesgo())
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con ID: " + dto.getIdZonaRiesgo())));
    }

    private IncidenteResponseDTO mapearAResponse(Incidente i) {
        IncidenteResponseDTO dto = new IncidenteResponseDTO();
        dto.setId(i.getId()); dto.setTipoIncidente(i.getTipoIncidente());
        dto.setDescripcion(i.getDescripcion()); dto.setSeveridad(i.getSeveridad());
        dto.setLatitud(i.getLatitud()); dto.setLongitud(i.getLongitud());
        dto.setEstado(i.getEstado()); dto.setFechaHora(i.getFechaHora());
        if (i.getServicio()    != null) dto.setCodigoServicio(i.getServicio().getCodigoServicio());
        if (i.getAgente()      != null) dto.setCodigoAgente(i.getAgente().getCodigoAgente());
        if (i.getZonaRiesgo()  != null) dto.setNombreZonaRiesgo(i.getZonaRiesgo().getNombre());
        return dto;
    }
}
