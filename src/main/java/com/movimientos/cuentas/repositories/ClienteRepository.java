package com.movimientos.cuentas.repositories;

import org.springframework.data.repository.CrudRepository;

import com.movimientos.cuentas.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}

