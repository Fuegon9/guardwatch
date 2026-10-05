package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.ZonaRiesgo;
import com.example.guardwatch.Repositories.ZonaRiesgoRepository;
import com.example.guardwatch.dtos.ZonaRiesgoRequestDTO;
import com.example.guardwatch.dtos.ZonaRiesgoResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZonaRiesgoService {

    @Autowired private ZonaRiesgoRepository zonaRepo;

    public ZonaRiesgoResponseDTO registrar(ZonaRiesgoRequestDTO dto) {
        ZonaRiesgo z = new ZonaRiesgo();
        z.setNombre(dto.getNombre()); z.setDescripcion(dto.getDescripcion());
        z.setDistrito(dto.getDistrito()); z.setProvincia(dto.getProvincia());
        z.setDepartamento(dto.getDepartamento()); z.setNivelRiesgo(dto.getNivelRiesgo());
        z.setLatitud(dto.getLatitud()); z.setLongitud(dto.getLongitud());
        z.setEstado(dto.getEstado());
        z.setCreatedAt(LocalDateTime.now()); z.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(zonaRepo.save(z));
    }

    public List<ZonaRiesgoResponseDTO> listarTodas() {
        return zonaRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public ZonaRiesgoResponseDTO buscarPorId(Long id) {
        return mapearAResponse(zonaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona de riesgo no encontrada con ID: " + id)));
    }

    public ZonaRiesgoResponseDTO actualizar(Long id, ZonaRiesgoRequestDTO dto) {
        ZonaRiesgo z = zonaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona de riesgo no encontrada con ID: " + id));
        z.setNombre(dto.getNombre()); z.setDescripcion(dto.getDescripcion());
        z.setDistrito(dto.getDistrito()); z.setProvincia(dto.getProvincia());
        z.setDepartamento(dto.getDepartamento()); z.setNivelRiesgo(dto.getNivelRiesgo());
        z.setLatitud(dto.getLatitud()); z.setLongitud(dto.getLongitud());
        z.setEstado(dto.getEstado()); z.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(zonaRepo.save(z));
    }

    public void eliminar(Long id) {
        if (!zonaRepo.existsById(id)) throw new ResourceNotFoundException("Zona de riesgo no encontrada con ID: " + id);
        zonaRepo.deleteById(id);
    }

    // Función derivada
    public List<ZonaRiesgoResponseDTO> buscarPorNivelRiesgo(String nivelRiesgo) {
        return zonaRepo.findByNivelRiesgo(nivelRiesgo).stream().map(this::mapearAResponse).toList();
    }

    public List<ZonaRiesgoResponseDTO> buscarPorProvinciaYEstado(String provincia, String estado) {
        return zonaRepo.findByProvinciaAndEstado(provincia, estado).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<ZonaRiesgoResponseDTO> buscarPorDistrito(String distrito) {
        return zonaRepo.buscarPorDistrito(distrito).stream().map(this::mapearAResponse).toList();
    }

    public List<ZonaRiesgoResponseDTO> listarZonasPeligrosasActivas() {
        return zonaRepo.listarZonasPeligrosasActivas().stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<ZonaRiesgoResponseDTO> buscarPorDepartamentoNativo(String depa) {
        return zonaRepo.buscarPorDepartamentoNativo(depa).stream().map(this::mapearAResponse).toList();
    }

    public List<Object[]> resumenZonasPorNivel() {
        return zonaRepo.resumenZonasPorNivelNativo();
    }

    private ZonaRiesgoResponseDTO mapearAResponse(ZonaRiesgo z) {
        ZonaRiesgoResponseDTO dto = new ZonaRiesgoResponseDTO();
        dto.setId(z.getId()); dto.setNombre(z.getNombre()); dto.setDescripcion(z.getDescripcion());
        dto.setDistrito(z.getDistrito()); dto.setProvincia(z.getProvincia());
        dto.setDepartamento(z.getDepartamento()); dto.setNivelRiesgo(z.getNivelRiesgo());
        dto.setLatitud(z.getLatitud()); dto.setLongitud(z.getLongitud()); dto.setEstado(z.getEstado());
        return dto;
    }
}
