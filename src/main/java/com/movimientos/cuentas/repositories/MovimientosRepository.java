package com.movimientos.cuentas.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movimientos.cuentas.entities.Cliente;
import com.movimientos.cuentas.entities.Cuenta;
import com.movimientos.cuentas.entities.Movimientos;

public interface MovimientosRepository extends CrudRepository<Movimientos, Long> {

List<Movimientos> findByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime startDate, LocalDateTime endDate);
List<Movimientos> findByCuentaInAndFechaBetween(List<Cuenta> cuentas, LocalDateTime startDate, LocalDateTime endDate);

}

