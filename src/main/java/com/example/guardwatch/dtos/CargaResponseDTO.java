package com.example.guardwatch.dtos;

public class CargaResponseDTO {
    private Long id;
    private String codigoCarga;
    private String descripcion;
    private String tipoCarga;
    private Double valorEstimado;
    private Double pesoKg;
    private String nivelRiesgo;
    private String estado;
    private String razonSocialCliente;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoCarga() { return codigoCarga; }
    public void setCodigoCarga(String c) { this.codigoCarga = c; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public String getTipoCarga() { return tipoCarga; }
    public void setTipoCarga(String t) { this.tipoCarga = t; }
    public Double getValorEstimado() { return valorEstimado; }
    public void setValorEstimado(Double v) { this.valorEstimado = v; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double p) { this.pesoKg = p; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String n) { this.nivelRiesgo = n; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public String getRazonSocialCliente() { return razonSocialCliente; }
    public void setRazonSocialCliente(String r) { this.razonSocialCliente = r; }
}