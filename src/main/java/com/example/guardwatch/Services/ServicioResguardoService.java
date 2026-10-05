package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.*;
import com.example.guardwatch.Repositories.*;
import com.example.guardwatch.dtos.ServicioResguardoRequestDTO;
import com.example.guardwatch.dtos.ServicioResguardoResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioResguardoService {

    @Autowired private ServicioResguardoRepository servicioRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private CargaRepository cargaRepo;
    @Autowired private VehiculoRepository vehiculoRepo;
    @Autowired private AgenteSeguridadRepository agenteRepo;

    public ServicioResguardoResponseDTO registrar(ServicioResguardoRequestDTO dto) {
        ServicioResguardo s = new ServicioResguardo();
        s.setCodigoServicio(dto.getCodigoServicio()); s.setTipoResguardo(dto.getTipoResguardo());
        s.setFechaInicio(dto.getFechaInicio()); s.setFechaFin(dto.getFechaFin());
        s.setEstado(dto.getEstado()); s.setPrioridad(dto.getPrioridad());
        s.setObservaciones(dto.getObservaciones());
        s.setCreatedAt(LocalDateTime.now()); s.setUpdatedAt(LocalDateTime.now());
        asignarRelaciones(s, dto);
        return mapearAResponse(servicioRepo.save(s));
    }

    public List<ServicioResguardoResponseDTO> listarTodos() {
        return servicioRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public ServicioResguardoResponseDTO buscarPorId(Long id) {
        return mapearAResponse(servicioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + id)));
    }

    public ServicioResguardoResponseDTO actualizar(Long id, ServicioResguardoRequestDTO dto) {
        ServicioResguardo s = servicioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + id));
        s.setCodigoServicio(dto.getCodigoServicio()); s.setTipoResguardo(dto.getTipoResguardo());
        s.setFechaInicio(dto.getFechaInicio()); s.setFechaFin(dto.getFechaFin());
        s.setEstado(dto.getEstado()); s.setPrioridad(dto.getPrioridad());
        s.setObservaciones(dto.getObservaciones()); s.setUpdatedAt(LocalDateTime.now());
        asignarRelaciones(s, dto);
        return mapearAResponse(servicioRepo.save(s));
    }

    public void eliminar(Long id) {
        if (!servicioRepo.existsById(id)) throw new ResourceNotFoundException("Servicio no encontrado con ID: " + id);
        servicioRepo.deleteById(id);
    }

    // Función derivada
    public List<ServicioResguardoResponseDTO> buscarPorEstado(String estado) {
        return servicioRepo.findByEstado(estado).stream().map(this::mapearAResponse).toList();
    }

    // CLIENTE_VIP y CLIENTE_NORMAL: ver solo sus propios servicios
    public List<ServicioResguardoResponseDTO> listarPorCliente(Long clienteId) {
        return servicioRepo.findByClienteId(clienteId).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<ServicioResguardoResponseDTO> buscarPorClienteYPrioridad(Long clienteId, String prioridad) {
        return servicioRepo.buscarPorClienteYPrioridad(clienteId, prioridad).stream().map(this::mapearAResponse).toList();
    }

    // Solo CLIENTE_VIP puede usar este filtro avanzado
    public List<ServicioResguardoResponseDTO> buscarPorClienteEstadoYPrioridad(Long clienteId, String estado, String prioridad) {
        return servicioRepo.buscarPorClienteEstadoYPrioridad(clienteId, estado, prioridad).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<ServicioResguardoResponseDTO> listarServiciosDeHoyNativo() {
        return servicioRepo.listarServiciosDeHoyNativo().stream().map(this::mapearAResponse).toList();
    }

    public List<ServicioResguardoResponseDTO> listarServiciosCriticosNativo() {
        return servicioRepo.listarServiciosCriticosNativo().stream().map(this::mapearAResponse).toList();
    }

    private void asignarRelaciones(ServicioResguardo s, ServicioResguardoRequestDTO dto) {
        if (dto.getIdCliente() != null) s.setCliente(clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getIdCliente())));
        if (dto.getIdCarga() != null) s.setCarga(cargaRepo.findById(dto.getIdCarga())
                .orElseThrow(() -> new ResourceNotFoundException("Carga no encontrada con ID: " + dto.getIdCarga())));
        if (dto.getIdVehiculo() != null) s.setVehiculo(vehiculoRepo.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + dto.getIdVehiculo())));
        if (dto.getIdsAgentes() != null) s.setAgentes(agenteRepo.findAllById(dto.getIdsAgentes()));
    }

    private ServicioResguardoResponseDTO mapearAResponse(ServicioResguardo s) {
        ServicioResguardoResponseDTO dto = new ServicioResguardoResponseDTO();
        dto.setId(s.getId()); dto.setCodigoServicio(s.getCodigoServicio());
        dto.setTipoResguardo(s.getTipoResguardo()); dto.setFechaInicio(s.getFechaInicio());
        dto.setFechaFin(s.getFechaFin()); dto.setEstado(s.getEstado());
        dto.setPrioridad(s.getPrioridad()); dto.setObservaciones(s.getObservaciones());
        if (s.getCliente()  != null) dto.setRazonSocialCliente(s.getCliente().getRazonSocial());
        if (s.getCarga()    != null) dto.setCodigoCarga(s.getCarga().getCodigoCarga());
        if (s.getVehiculo() != null) dto.setPlacaVehiculo(s.getVehiculo().getPlaca());
        if (s.getAgentes()  != null) dto.setCodigosAgentes(s.getAgentes().stream().map(AgenteSeguridad::getCodigoAgente).toList());
        return dto;
    }
}
