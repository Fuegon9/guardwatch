package com.example.guardwatch.Services;

import com.example.guardwatch.Entities.HistorialServicio;
import com.example.guardwatch.Entities.ServicioResguardo;
import com.example.guardwatch.Repositories.HistorialServicioRepository;
import com.example.guardwatch.Repositories.ServicioResguardoRepository;
import com.example.guardwatch.dtos.HistorialServicioRequestDTO;
import com.example.guardwatch.dtos.HistorialServicioResponseDTO;
import com.example.guardwatch.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialServicioService {

    @Autowired private HistorialServicioRepository historialRepo;
    @Autowired private ServicioResguardoRepository servicioRepo;

    public HistorialServicioResponseDTO registrar(HistorialServicioRequestDTO dto) {
        ServicioResguardo s = servicioRepo.findById(dto.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + dto.getIdServicio()));
        HistorialServicio h = new HistorialServicio();
        h.setEstadoAnterior(dto.getEstadoAnterior()); h.setEstadoNuevo(dto.getEstadoNuevo());
        h.setComentario(dto.getComentario()); h.setFechaHora(dto.getFechaHora()); h.setServicio(s);
        return mapearAResponse(historialRepo.save(h));
    }

    // Función derivada
    public List<HistorialServicioResponseDTO> listarPorServicio(Long servicioId) {
        return historialRepo.findByServicioIdOrderByFechaHoraDesc(servicioId).stream().map(this::mapearAResponse).toList();
    }

    public List<HistorialServicioResponseDTO> listarPorEstadoNuevo(String estadoNuevo) {
        return historialRepo.findByEstadoNuevo(estadoNuevo).stream().map(this::mapearAResponse).toList();
    }

    // JPQL
    public List<HistorialServicioResponseDTO> buscarPorEstadoNuevo(String nuevoEstado) {
        return historialRepo.buscarPorEstadoNuevo(nuevoEstado).stream().map(this::mapearAResponse).toList();
    }

    public List<HistorialServicioResponseDTO> buscarTransicionEspecifica(Long servicioId, String anterior, String nuevo) {
        return historialRepo.buscarTransicionEspecifica(servicioId, anterior, nuevo).stream().map(this::mapearAResponse).toList();
    }

    // SQL Nativo
    public List<HistorialServicioResponseDTO> buscarHistorialNativo(Long servicioId) {
        return historialRepo.buscarHistorialPorServicioNativo(servicioId).stream().map(this::mapearAResponse).toList();
    }

    public List<Object[]> serviciosConMasCambios(Long umbral) {
        return historialRepo.serviciosConMasCambiosNativo(umbral);
    }

    private HistorialServicioResponseDTO mapearAResponse(HistorialServicio h) {
        HistorialServicioResponseDTO dto = new HistorialServicioResponseDTO();
        dto.setId(h.getId()); dto.setEstadoAnterior(h.getEstadoAnterior());
        dto.setEstadoNuevo(h.getEstadoNuevo()); dto.setComentario(h.getComentario());
        dto.setFechaHora(h.getFechaHora());
        if (h.getServicio() != null) dto.setCodigoServicio(h.getServicio().getCodigoServicio());
        return dto;
    }
}
