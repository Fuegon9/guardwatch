package com.example.guardwatch.dtos;

public class AgenteSeguridadRequestDTO {
    private String codigoAgente;
    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String licencia;
    private Double experienciaAnios;
    private String especializacion;
    private String disponibilidad;
    private String estado;

    public String getCodigoAgente() { return codigoAgente; }
    public void setCodigoAgente(String c) { this.codigoAgente = c; }
    public String getDni() { return dni; }
    public void setDni(String d) { this.dni = d; }
    public String getNombres() { return nombres; }
    public void setNombres(String n) { this.nombres = n; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String a) { this.apellidos = a; }
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