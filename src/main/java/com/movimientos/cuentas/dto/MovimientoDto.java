package com.movimientos.cuentas.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MovimientoDto {
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private Long cuentaId;

}
