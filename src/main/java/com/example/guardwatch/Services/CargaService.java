package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.Carga;
import com.example.guardwatch.Entities.Cliente;
import com.example.guardwatch.Repositories.CargaRepository;
import com.example.guardwatch.Repositories.ClienteRepository;
import com.example.guardwatch.dtos.CargaRequestDTO;
import com.example.guardwatch.dtos.CargaResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CargaService {

    @Autowired private CargaRepository cargaRepo;
    @Autowired private ClienteRepository clienteRepo;

    public CargaResponseDTO registrar(CargaRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getIdCliente()));
        Carga c = new Carga();
        c.setCodigoCarga(dto.getCodigoCarga()); c.setDescripcion(dto.getDescripcion());
        c.setTipoCarga(dto.getTipoCarga()); c.setValorEstimado(dto.getValorEstimado());
        c.setPesoKg(dto.getPesoKg()); c.setNivelRiesgo(dto.getNivelRiesgo());
        c.setEstado(dto.getEstado()); c.setCliente(cliente);
        c.setCreatedAt(LocalDateTime.now()); c.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(cargaRepo.save(c));
    }

    public List<CargaResponseDTO> listarTodas() {
        return cargaRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public CargaResponseDTO buscarPorId(Long id) {
        return mapearAResponse(cargaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carga no encontrada con ID: " + id)));
    }

    public CargaResponseDTO actualizar(Long id, CargaRequestDTO dto) {
        Carga c = cargaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carga no encontrada con ID: " + id));
        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepo.findById(dto.getIdCliente())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getIdCliente()));
            c.setCliente(cliente);
        }
        c.setCodigoCarga(dto.getCodigoCarga()); c.setDescripcion(dto.getDescripcion());
        c.setTipoCarga(dto.getTipoCarga()); c.setValorEstimado(dto.getValorEstimado());
        c.setPesoKg(dto.getPesoKg()); c.setNivelRiesgo(dto.getNivelRiesgo());
        c.setEstado(dto.getEstado()); c.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(cargaRepo.save(c));
    }

    public void eliminar(Long id) {
        if (!cargaRepo.existsById(id)) throw new ResourceNotFoundException("Carga no encontrada con ID: " + id);
        cargaRepo.deleteById(id);
    }

    // Función derivada
    public List<CargaResponseDTO> listarPorCliente(Long clienteId) {
        return cargaRepo.findByClienteId(clienteId).stream().map(this::mapearAResponse).toList();
    }

    public List<CargaResponseDTO> listarPorEstado(String estado) {
        return cargaRepo.findByEstado(estado).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<CargaResponseDTO> buscarPorRiesgoYPesoMinimo(String riesgo, Double pesoMin) {
        return cargaRepo.buscarPorRiesgoYPesoMinimo(riesgo, pesoMin).stream().map(this::mapearAResponse).toList();
    }

    public List<CargaResponseDTO> buscarPorClienteYRiesgo(Long clienteId, String riesgo) {
        return cargaRepo.buscarPorClienteYRiesgo(clienteId, riesgo).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<CargaResponseDTO> buscarAltoValorNativo(Double valorMin) {
        return cargaRepo.buscarCargasDeAltoValorNativo(valorMin).stream().map(this::mapearAResponse).toList();
    }

    public List<Object[]> contarCargasPorTipo() {
        return cargaRepo.contarCargasPorTipoNativo();
    }

    private CargaResponseDTO mapearAResponse(Carga c) {
        CargaResponseDTO dto = new CargaResponseDTO();
        dto.setId(c.getId()); dto.setCodigoCarga(c.getCodigoCarga());
        dto.setDescripcion(c.getDescripcion()); dto.setTipoCarga(c.getTipoCarga());
        dto.setValorEstimado(c.getValorEstimado()); dto.setPesoKg(c.getPesoKg());
        dto.setNivelRiesgo(c.getNivelRiesgo()); dto.setEstado(c.getEstado());
        if (c.getCliente() != null) dto.setRazonSocialCliente(c.getCliente().getRazonSocial());
        return dto;
    }
}
