package com.example.guardwatch.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AgenteSeguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "agentes")
    private List<ServicioResguardo> servicios;
}