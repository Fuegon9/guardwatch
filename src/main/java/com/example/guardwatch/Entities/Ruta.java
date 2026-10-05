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
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private Double distanciaKm;
    private Integer tiempoEstimadoMinutos;
    private String nivelRiesgo;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", unique = true)
    private ServicioResguardo servicio;

    @ManyToMany
    @JoinTable(
            name = "ruta_zona_riesgo",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "zona_riesgo_id")
    )
    private List<ZonaRiesgo> zonasRiesgo;
}