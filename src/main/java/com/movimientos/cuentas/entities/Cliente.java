package com.movimientos.cuentas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Persona {
    @Column(unique = true, nullable = false)
    private String clienteid;
    private String contrasena;
    private boolean estado;

    // Getters y setters
}



