package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.Vehiculo;
import com.example.guardwatch.Repositories.VehiculoRepository;
import com.example.guardwatch.dtos.VehiculoRequestDTO;
import com.example.guardwatch.dtos.VehiculoResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehiculoService {

    @Autowired private VehiculoRepository vehiculoRepo;

    public VehiculoResponseDTO registrar(VehiculoRequestDTO dto) {
        Vehiculo v = new Vehiculo();
        v.setPlaca(dto.getPlaca()); v.setTipoVehiculo(dto.getTipoVehiculo());
        v.setMarca(dto.getMarca()); v.setModelo(dto.getModelo());
        v.setAnio(dto.getAnio()); v.setCapacidadKg(dto.getCapacidadKg());
        v.setEstado(dto.getEstado());
        v.setCreatedAt(LocalDateTime.now()); v.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(vehiculoRepo.save(v));
    }

    public List<VehiculoResponseDTO> listarTodos() {
        return vehiculoRepo.findAll().stream().map(this::mapearAResponse).toList();
    }

    public VehiculoResponseDTO buscarPorId(Long id) {
        return mapearAResponse(vehiculoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + id)));
    }

    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO dto) {
        Vehiculo v = vehiculoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + id));
        v.setPlaca(dto.getPlaca()); v.setTipoVehiculo(dto.getTipoVehiculo());
        v.setMarca(dto.getMarca()); v.setModelo(dto.getModelo());
        v.setAnio(dto.getAnio()); v.setCapacidadKg(dto.getCapacidadKg());
        v.setEstado(dto.getEstado()); v.setUpdatedAt(LocalDateTime.now());
        return mapearAResponse(vehiculoRepo.save(v));
    }

    public void eliminar(Long id) {
        if (!vehiculoRepo.existsById(id)) throw new ResourceNotFoundException("Vehículo no encontrado con ID: " + id);
        vehiculoRepo.deleteById(id);
    }

    // Función derivada
    public List<VehiculoResponseDTO> buscarPorEstado(String estado) {
        return vehiculoRepo.findByEstado(estado).stream().map(this::mapearAResponse).toList();
    }

    public List<VehiculoResponseDTO> buscarPorTipo(String tipo) {
        return vehiculoRepo.findByTipoVehiculo(tipo).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<VehiculoResponseDTO> buscarPorCapacidadMinima(Double capacidadMin) {
        return vehiculoRepo.buscarPorCapacidadMinima(capacidadMin).stream().map(this::mapearAResponse).toList();
    }

    public List<VehiculoResponseDTO> buscarDisponiblesPorTipo(String tipo) {
        return vehiculoRepo.buscarDisponiblesPorTipo(tipo).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public VehiculoResponseDTO buscarPorPlacaNativo(String placa) {
        Vehiculo v = vehiculoRepo.buscarPorPlacaNativo(placa);
        if (v == null) throw new ResourceNotFoundException("Vehículo no encontrado con placa: " + placa);
        return mapearAResponse(v);
    }

    public List<VehiculoResponseDTO> buscarVehiculosAntiguos(Integer anioMaximo) {
        return vehiculoRepo.buscarVehiculosAntiguosNativo(anioMaximo).stream().map(this::mapearAResponse).toList();
    }

    private VehiculoResponseDTO mapearAResponse(Vehiculo v) {
        VehiculoResponseDTO dto = new VehiculoResponseDTO();
        dto.setId(v.getId()); dto.setPlaca(v.getPlaca()); dto.setTipoVehiculo(v.getTipoVehiculo());
        dto.setMarca(v.getMarca()); dto.setModelo(v.getModelo());
        dto.setAnio(v.getAnio()); dto.setCapacidadKg(v.getCapacidadKg()); dto.setEstado(v.getEstado());
        return dto;
    }
}
