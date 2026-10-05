package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class IncidenteRequestDTO {
    private String tipoIncidente;
    private String descripcion;
    private String severidad;
    private Double latitud;
    private Double longitud;
    private String estado;
    private LocalDateTime fechaHora;
    private Long idServicio;
    private Long idAgente;
    private Long idZonaRiesgo;

    public String getTipoIncidente() { return tipoIncidente; }
    public void setTipoIncidente(String t) { this.tipoIncidente = t; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String s) { this.severidad = s; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double l) { this.latitud = l; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double l) { this.longitud = l; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime f) { this.fechaHora = f; }
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long id) { this.idServicio = id; }
    public Long getIdAgente() { return idAgente; }
    public void setIdAgente(Long id) { this.idAgente = id; }
    public Long getIdZonaRiesgo() { return idZonaRiesgo; }
    public void setIdZonaRiesgo(Long id) { this.idZonaRiesgo = id; }
}