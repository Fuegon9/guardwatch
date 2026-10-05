package com.example.guardwatch.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String tipoVehiculo;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double capacidadKg;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}