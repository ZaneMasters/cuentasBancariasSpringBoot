package com.movimientos.cuentas.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movimientos.cuentas.entities.Cuenta;

public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    List<Cuenta> findByClienteId(Long clienteId);
}

