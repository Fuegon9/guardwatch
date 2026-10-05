package com.example.guardwatch.dtos;

import java.time.LocalDateTime;

public class HistorialServicioResponseDTO {
    private Long id;
    private String estadoAnterior;
    private String estadoNuevo;
    private String comentario;
    private LocalDateTime fechaHora;
    private String codigoServicio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String e) { this.estadoAnterior = e; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String e) { this.estadoNuevo = e; }
    public String getComentario() { return comentario; }
    public void setComentario(String c) { this.comentario = c; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime f) { this.fechaHora = f; }
    public String getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(String c) { this.codigoServicio = c; }
}