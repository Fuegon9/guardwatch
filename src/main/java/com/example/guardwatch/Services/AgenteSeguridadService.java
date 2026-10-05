package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.AgenteSeguridad;
import com.example.guardwatch.Repositories.AgenteSeguridadRepository;
import com.example.guardwatch.dtos.AgenteSeguridadRequestDTO;
import com.example.guardwatch.dtos.AgenteSeguridadResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgenteSeguridadService {

    @Autowired private AgenteSeguridadRepository agenteRepo;

    public AgenteSeguridadResponseDTO registrar(AgenteSeguridadRequestDTO dto) {
        AgenteSeguridad a = new AgenteSeguridad();
        a.setCodigoAgente(dto.getCodigoAgente());
        a.setDni(dto.getDni());
        a.setNombres(dto.getNombres());
        a.setApellidos(dto.getApellidos());
        a.setTelefono(dto.getTelefono());
        a.setLicencia(dto.getLicencia());
        a.setExperienciaAnios(dto.getExperienciaAnios());
        a.setEspecializacion(dto.getEspecializacion());
        a.setDisponibilidad(dto.getDisponibilidad());
        a.setEstado(dto.getEstado());
        a.setCreatedAt(LocalDateTime.now());
        a.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(agenteRepo.save(a));
    }

    public List<AgenteSeguridadResponseDTO> listarTodos() {
        return agenteRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public AgenteSeguridadResponseDTO buscarPorId(Long id) {
        return mapearAResponse(agenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agente no encontrado con ID: " + id)));
    }

    public AgenteSeguridadResponseDTO actualizar(Long id, AgenteSeguridadRequestDTO dto) {
        AgenteSeguridad a = agenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agente no encontrado con ID: " + id));
        a.setCodigoAgente(dto.getCodigoAgente()); a.setDni(dto.getDni());
        a.setNombres(dto.getNombres()); a.setApellidos(dto.getApellidos());
        a.setTelefono(dto.getTelefono()); a.setLicencia(dto.getLicencia());
        a.setExperienciaAnios(dto.getExperienciaAnios()); a.setEspecializacion(dto.getEspecializacion());
        a.setDisponibilidad(dto.getDisponibilidad()); a.setEstado(dto.getEstado());
        a.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(agenteRepo.save(a));
    }

    public void eliminar(Long id) {
        if (!agenteRepo.existsById(id)) throw new ResourceNotFoundException("Agente no encontrado con ID: " + id);
        agenteRepo.deleteById(id);
    }

    // Función derivada
    public List<AgenteSeguridadResponseDTO> buscarPorEstadoYDisponibilidad(String estado, String disponibilidad) {
        return agenteRepo.findByEstadoAndDisponibilidad(estado, disponibilidad).stream().map(this::mapearAResponse).toList();
    }

    public List<AgenteSeguridadResponseDTO> buscarPorLicencia(String licencia) {
        return agenteRepo.findByLicencia(licencia).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<AgenteSeguridadResponseDTO> buscarPorExperienciaMinima(Double minExperiencia) {
        return agenteRepo.buscarPorExperienciaMinima(minExperiencia).stream().map(this::mapearAResponse).toList();
    }

    public List<Object[]> contarAgentesPorEstado() {
        return agenteRepo.contarAgentesPorEstado();
    }

    // SQL Nativo
    public List<AgenteSeguridadResponseDTO> buscarPorEspecializacionNativo(String especializacion) {
        return agenteRepo.buscarPorEspecializacionNativo(especializacion).stream().map(this::mapearAResponse).toList();
    }

    public List<AgenteSeguridadResponseDTO> listarAgentesLibresHoy() {
        return agenteRepo.listarAgentesLibresHoyNativo().stream().map(this::mapearAResponse).toList();
    }

    private AgenteSeguridadResponseDTO mapearAResponse(AgenteSeguridad a) {
        AgenteSeguridadResponseDTO dto = new AgenteSeguridadResponseDTO();
        dto.setId(a.getId()); dto.setCodigoAgente(a.getCodigoAgente());
        dto.setDni(a.getDni()); dto.setNombreCompleto(a.getNombres() + " " + a.getApellidos());
        dto.setTelefono(a.getTelefono()); dto.setLicencia(a.getLicencia());
        dto.setExperienciaAnios(a.getExperienciaAnios()); dto.setEspecializacion(a.getEspecializacion());
        dto.setDisponibilidad(a.getDisponibilidad()); dto.setEstado(a.getEstado());
        return dto;
    }
}
