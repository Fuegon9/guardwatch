package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class SeguimientoResponseDTO {
    private Long id;
    private Double latitud;
    private Double longitud;
    private Double velocidadKmh;
    private String estado;
    private LocalDateTime fechaHora;
    private String codigoServicio;
    private String placaVehiculo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double l) { this.latitud = l; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double l) { this.longitud = l; }
    public Double getVelocidadKmh() { return velocidadKmh; }
    public void setVelocidadKmh(Double v) { this.velocidadKmh = v; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime f) { this.fechaHora = f; }
    public String getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(String c) { this.codigoServicio = c; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String p) { this.placaVehiculo = p; }
}