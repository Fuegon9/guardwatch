package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class IncidenteResponseDTO {
    private Long id;
    private String tipoIncidente;
    private String descripcion;
    private String severidad;
    private Double latitud;
    private Double longitud;
    private String estado;
    private LocalDateTime fechaHora;
    private String codigoServicio;
    private String codigoAgente;
    private String nombreZonaRiesgo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public String getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(String c) { this.codigoServicio = c; }
    public String getCodigoAgente() { return codigoAgente; }
    public void setCodigoAgente(String c) { this.codigoAgente = c; }
    public String getNombreZonaRiesgo() { return nombreZonaRiesgo; }
    public void setNombreZonaRiesgo(String n) { this.nombreZonaRiesgo = n; }
}