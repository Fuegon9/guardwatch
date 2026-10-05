package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.Cliente;
import com.example.guardwatch.Repositories.ClienteRepository;
import com.example.guardwatch.dtos.ClienteRequestDTO;
import com.example.guardwatch.dtos.ClienteResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired private ClienteRepository clienteRepo;

    public ClienteResponseDTO registrar(ClienteRequestDTO dto) {
        Cliente c = new Cliente();
        c.setRazonSocial(dto.getRazonSocial()); c.setRuc(dto.getRuc());
        c.setDireccion(dto.getDireccion()); c.setTelefono(dto.getTelefono());
        c.setEmailContacto(dto.getEmailContacto()); c.setEstado(dto.getEstado());
        c.setCreatedAt(LocalDateTime.now()); c.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(clienteRepo.save(c));
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return mapearAResponse(clienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id)));
    }

    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente c = clienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        c.setRazonSocial(dto.getRazonSocial()); c.setRuc(dto.getRuc());
        c.setDireccion(dto.getDireccion()); c.setTelefono(dto.getTelefono());
        c.setEmailContacto(dto.getEmailContacto()); c.setEstado(dto.getEstado());
        c.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(clienteRepo.save(c));
    }

    public void eliminar(Long id) {
        if (!clienteRepo.existsById(id)) throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        clienteRepo.deleteById(id);
    }

    // Función derivada
    public ClienteResponseDTO buscarPorRuc(String ruc) {
        return mapearAResponse(clienteRepo.findByRuc(ruc)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con RUC: " + ruc)));
    }

    public List<ClienteResponseDTO> buscarPorEstado(String estado) {
        return clienteRepo.findByEstado(estado).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<ClienteResponseDTO> buscarPorRazonSocial(String nombre) {
        return clienteRepo.buscarPorRazonSocial(nombre).stream().map(this::mapearAResponse).toList();
    }

    public List<ClienteResponseDTO> buscarClientesConServicioEnEstado(String estadoServicio) {
        return clienteRepo.buscarClientesConServicioEnEstado(estadoServicio).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<ClienteResponseDTO> listarActivosNativo() {
        return clienteRepo.listarClientesActivosNativo().stream().map(this::mapearAResponse).toList();
    }

    public List<ClienteResponseDTO> listarPorActividadNativo() {
        return clienteRepo.listarClientesPorActividadNativo().stream().map(this::mapearAResponse).toList();
    }

    private ClienteResponseDTO mapearAResponse(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(c.getId()); dto.setRazonSocial(c.getRazonSocial()); dto.setRuc(c.getRuc());
        dto.setDireccion(c.getDireccion()); dto.setTelefono(c.getTelefono());
        dto.setEmailContacto(c.getEmailContacto()); dto.setEstado(c.getEstado());
        return dto;
    }
}
