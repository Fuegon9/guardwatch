package com.example.guardwatch.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ServicioResguardoResponseDTO {
    private Long id;
    private String codigoServicio;
    private String tipoResguardo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String prioridad;
    private String observaciones;
    private String razonSocialCliente;
    private String codigoCarga;
    private String placaVehiculo;
    private List<String> codigosAgentes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public String getRazonSocialCliente() { return razonSocialCliente; }
    public void setRazonSocialCliente(String r) { this.razonSocialCliente = r; }
    public String getCodigoCarga() { return codigoCarga; }
    public void setCodigoCarga(String c) { this.codigoCarga = c; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String p) { this.placaVehiculo = p; }
    public List<String> getCodigosAgentes() { return codigosAgentes; }
    public void setCodigosAgentes(List<String> c) { this.codigosAgentes = c; }
}