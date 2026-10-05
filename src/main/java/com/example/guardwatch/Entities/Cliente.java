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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String emailContacto;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}