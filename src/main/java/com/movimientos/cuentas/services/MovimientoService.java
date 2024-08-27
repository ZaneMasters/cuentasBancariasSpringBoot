package com.movimientos.cuentas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.movimientos.cuentas.dto.MovimientoDto;
import com.movimientos.cuentas.entities.Cuenta;
import com.movimientos.cuentas.entities.Movimientos;
import com.movimientos.cuentas.exception.ResourceNotFoundException;
import com.movimientos.cuentas.repositories.CuentaRepository;
import com.movimientos.cuentas.repositories.MovimientosRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoService {

    private static final double DAILY_WITHDRAWAL_LIMIT = 1000.0;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimientos> getAllMovimientos() {
        return (List<Movimientos>) movimientosRepository.findAll();
    }

    public ResponseEntity<Movimientos> getMovimientoById(Long id) {
        Movimientos movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));
        return ResponseEntity.ok().body(movimiento);
    }

    public ResponseEntity<Movimientos> createMovimiento(MovimientoDto movimientoDto) {
        Cuenta cuenta = cuentaRepository.findById(movimientoDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + movimientoDto.getCuentaId()));

        Movimientos movimiento = new Movimientos();
        movimiento.setFecha(movimientoDto.getFecha());
        movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        movimiento.setValor(movimientoDto.getValor());
        movimiento.setCuenta(cuenta);

        // Validación de saldo y actualización de cuenta
        if (movimientoDto.getTipoMovimiento().equalsIgnoreCase("DEBITO")) {
            double totalWithdrawalsToday = calculateTotalWithdrawalsToday(cuenta.getId());
            if (totalWithdrawalsToday + movimientoDto.getValor() > DAILY_WITHDRAWAL_LIMIT) {
                throw new RuntimeException("Límite diario de retiro excedido");
            }
            if (cuenta.getSaldoInicial() < movimientoDto.getValor()) {
                throw new RuntimeException("Saldo insuficiente para realizar el retiro");
            }
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimientoDto.getValor());
            movimiento.setSaldo(cuenta.getSaldoInicial());
        } else if (movimientoDto.getTipoMovimiento().equalsIgnoreCase("CREDITO")) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimientoDto.getValor());
            movimiento.setSaldo(cuenta.getSaldoInicial());
        }

        cuentaRepository.save(cuenta);
        Movimientos savedMovimiento = movimientosRepository.save(movimiento);

        return ResponseEntity.ok(savedMovimiento);
    }

    public ResponseEntity<Movimientos> updateMovimiento(Long id, MovimientoDto movimientoDto) {
        Movimientos movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));

        Cuenta cuenta = cuentaRepository.findById(movimientoDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + movimientoDto.getCuentaId()));

        movimiento.setFecha(movimientoDto.getFecha());
        movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        movimiento.setValor(movimientoDto.getValor());
        movimiento.setCuenta(cuenta);

        if (movimientoDto.getTipoMovimiento().equalsIgnoreCase("DEBITO")) {
            if (cuenta.getSaldoInicial() < movimientoDto.getValor()) {
                throw new RuntimeException("Saldo insuficiente para realizar el retiro");
            }
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimientoDto.getValor());
        } else if (movimientoDto.getTipoMovimiento().equalsIgnoreCase("CREDITO")) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimientoDto.getValor());
        }

        cuentaRepository.save(cuenta);
        Movimientos updatedMovimiento = movimientosRepository.save(movimiento);

        return ResponseEntity.ok(updatedMovimiento);
    }

    public ResponseEntity<Void> deleteMovimiento(Long id) {
        Movimientos movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));
        movimientosRepository.delete(movimiento);
        return ResponseEntity.noContent().build();
    }

    private double calculateTotalWithdrawalsToday(Long cuentaId) {
        List<Movimientos> movimientosToday = movimientosRepository
                .findByCuentaAndFechaBetween(
                        cuentaRepository.findById(cuentaId)
                                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + cuentaId)),
                        LocalDate.now().atStartOfDay(),
                        LocalDateTime.now()
                );

        return movimientosToday.stream()
                .filter(mov -> mov.getTipoMovimiento().equalsIgnoreCase("DEBITO"))
                .mapToDouble(Movimientos::getValor)
                .sum();
    }
}
