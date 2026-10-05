package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class SeguimientoRequestDTO {
    private Double latitud;
    private Double longitud;
    private Double velocidadKmh;
    private String estado;
    private LocalDateTime fechaHora;
    private Long idServicio;
    private Long idVehiculo;

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
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long id) { this.idServicio = id; }
    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long id) { this.idVehiculo = id; }
}