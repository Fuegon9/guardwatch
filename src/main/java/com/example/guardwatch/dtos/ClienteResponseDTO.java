package com.example.guardwatch.dtos;

public class ClienteResponseDTO {
    private Long id;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String emailContacto;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String r) { this.razonSocial = r; }
    public String getRuc() { return ruc; }
    public void setRuc(String r) { this.ruc = r; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String d) { this.direccion = d; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public String getEmailContacto() { return emailContacto; }
    public void setEmailContacto(String e) { this.emailContacto = e; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
}