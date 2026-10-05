package com.example.guardwatch.dtos;

public class ZonaRiesgoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String nivelRiesgo;
    private Double latitud;
    private Double longitud;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String d) { this.distrito = d; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String p) { this.provincia = p; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String d) { this.departamento = d; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String n) { this.nivelRiesgo = n; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double l) { this.latitud = l; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double l) { this.longitud = l; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
}