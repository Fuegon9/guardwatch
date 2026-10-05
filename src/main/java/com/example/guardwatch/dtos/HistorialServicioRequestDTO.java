package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class HistorialServicioRequestDTO {
    private String estadoAnterior;
    private String estadoNuevo;
    private String comentario;
    private LocalDateTime fechaHora;
    private Long idServicio;

    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String e) { this.estadoAnterior = e; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String e) { this.estadoNuevo = e; }
    public String getComentario() { return comentario; }
    public void setComentario(String c) { this.comentario = c; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime f) { this.fechaHora = f; }
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long id) { this.idServicio = id; }
}