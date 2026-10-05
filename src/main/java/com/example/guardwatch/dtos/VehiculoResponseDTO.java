package com.example.guardwatch.dtos;

public class VehiculoResponseDTO {
    private Long id;
    private String placa;
    private String tipoVehiculo;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double capacidadKg;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String p) { this.placa = p; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String t) { this.tipoVehiculo = t; }
    public String getMarca() { return marca; }
    public void setMarca(String m) { this.marca = m; }
    public String getModelo() { return modelo; }
    public void setModelo(String m) { this.modelo = m; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer a) { this.anio = a; }
    public Double getCapacidadKg() { return capacidadKg; }
    public void setCapacidadKg(Double c) { this.capacidadKg = c; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
}