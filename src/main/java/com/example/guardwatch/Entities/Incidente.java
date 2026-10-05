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
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoIncidente;
    private String descripcion;
    private String severidad;
    private Double latitud;
    private Double longitud;
    private String estado;
    private LocalDateTime fechaHora;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private ServicioResguardo servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private AgenteSeguridad agente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_riesgo_id")
    private ZonaRiesgo zonaRiesgo;
}