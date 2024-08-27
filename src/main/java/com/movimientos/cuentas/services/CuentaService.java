package com.movimientos.cuentas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.movimientos.cuentas.dto.CuentaDto;
import com.movimientos.cuentas.entities.Cliente;
import com.movimientos.cuentas.entities.Cuenta;
import com.movimientos.cuentas.repositories.ClienteRepository;
import com.movimientos.cuentas.repositories.CuentaRepository;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cuenta> getAllCuentas() {
        return (List<Cuenta>) cuentaRepository.findAll();
    }

    public ResponseEntity<Cuenta> getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return ResponseEntity.ok(cuenta);
    }

    public Cuenta createCuenta(CuentaDto cuentaDto) {
        Cliente cliente = clienteRepository.findById(cuentaDto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuenta.setEstado(cuentaDto.isEstado());
        cuenta.setCliente(cliente);

        return cuentaRepository.save(cuenta);
    }

    public ResponseEntity<Cuenta> updateCuenta(Long id, CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuenta.setEstado(cuentaDto.isEstado());

        final Cuenta updatedCuenta = cuentaRepository.save(cuenta);
        return ResponseEntity.ok(updatedCuenta);
    }

    public ResponseEntity<Void> deleteCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuentaRepository.delete(cuenta);
        return ResponseEntity.noContent().build();
    }
}