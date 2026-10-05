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
public class ServicioResguardo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoServicio;
    private String tipoResguardo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String prioridad;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carga_id")
    private Carga carga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToMany
    @JoinTable(
            name = "servicio_agente",
            joinColumns = @JoinColumn(name = "servicio_id"),
            inverseJoinColumns = @JoinColumn(name = "agente_id")
    )
    private List<AgenteSeguridad> agentes;
}