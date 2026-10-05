package com.example.guardwatch.dtos;

import java.util.List;

public class RutaRequestDTO {
    private String origen;
    private String destino;
    private Double distanciaKm;
    private Integer tiempoEstimadoMinutos;
    private String nivelRiesgo;
    private String estado;
    private Long idServicio;
    private List<Long> idsZonasRiesgo;

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
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long id) { this.idServicio = id; }
    public List<Long> getIdsZonasRiesgo() { return idsZonasRiesgo; }
    public void setIdsZonasRiesgo(List<Long> ids) { this.idsZonasRiesgo = ids; }
}