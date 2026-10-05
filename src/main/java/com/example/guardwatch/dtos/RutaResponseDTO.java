package com.example.guardwatch.dtos;

import java.util.List;

public class RutaResponseDTO {
    private Long id;
    private String origen;
    private String destino;
    private Double distanciaKm;
    private Integer tiempoEstimadoMinutos;
    private String nivelRiesgo;
    private String estado;
    private String codigoServicio;
    private List<String> nombresZonasRiesgo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrigen() { return origen; }
    public void setOrigen(String o) { this.origen = o; }
    public String getDestino() { return destino; }
    public void setDestino(String d) { this.destino = d; }
    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double d) { this.distanciaKm = d; }
    public Integer getTiempoEstimadoMinutos() { return tiempoEstimadoMinutos; }
    public void setTiempoEstimadoMinutos(Integer t) { this.tiempoEstimadoMinutos = t; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String n) { this.nivelRiesgo = n; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public String getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(String c) { this.codigoServicio = c; }
    public List<String> getNombresZonasRiesgo() { return nombresZonasRiesgo; }
    public void setNombresZonasRiesgo(List<String> n) { this.nombresZonasRiesgo = n; }
}