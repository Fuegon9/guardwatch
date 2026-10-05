package com.example.guardwatch.dtos;

public class AgenteSeguridadResponseDTO {
    private Long id;
    private String codigoAgente;
    private String dni;
    private String nombreCompleto;
    private String telefono;
    private String licencia;
    private Double experienciaAnios;
    private String especializacion;
    private String disponibilidad;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoAgente() { return codigoAgente; }
    public void setCodigoAgente(String c) { this.codigoAgente = c; }
    public String getDni() { return dni; }
    public void setDni(String d) { this.dni = d; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String n) { this.nombreCompleto = n; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public String getLicencia() { return licencia; }
    public void setLicencia(String l) { this.licencia = l; }
    public Double getExperienciaAnios() { return experienciaAnios; }
    public void setExperienciaAnios(Double e) { this.experienciaAnios = e; }
    public String getEspecializacion() { return especializacion; }
    public void setEspecializacion(String e) { this.especializacion = e; }
    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String d) { this.disponibilidad = d; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
}