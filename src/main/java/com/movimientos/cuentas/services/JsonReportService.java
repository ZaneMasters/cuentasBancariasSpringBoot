package com.movimientos.cuentas.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movimientos.cuentas.entities.Cliente;
import com.movimientos.cuentas.entities.Movimientos;
import com.movimientos.cuentas.repositories.ClienteRepository;
import com.movimientos.cuentas.repositories.MovimientosRepository;

@Service
public class JsonReportService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private ObjectMapper objectMapper;


}
