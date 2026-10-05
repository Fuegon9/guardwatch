package com.example.guardwatch.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ServicioResguardoRequestDTO {
    private String codigoServicio;
    private String tipoResguardo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String prioridad;
    private String observaciones;
    private Long idCliente;
    private Long idCarga;
    private Long idVehiculo;
    private List<Long> idsAgentes;

    public String getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(String c) { this.codigoServicio = c; }
    public String getTipoResguardo() { return tipoResguardo; }
    public void setTipoResguardo(String t) { this.tipoResguardo = t; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime f) { this.fechaInicio = f; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime f) { this.fechaFin = f; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String p) { this.prioridad = p; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String o) { this.observaciones = o; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long id) { this.idCliente = id; }
    public Long getIdCarga() { return idCarga; }
    public void setIdCarga(Long id) { this.idCarga = id; }
    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long id) { this.idVehiculo = id; }
    public List<Long> getIdsAgentes() { return idsAgentes; }
    public void setIdsAgentes(List<Long> ids) { this.idsAgentes = ids; }
}