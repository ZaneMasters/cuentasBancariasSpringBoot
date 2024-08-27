package com.movimientos.cuentas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movimientos.cuentas.dto.MovimientoDto;
import com.movimientos.cuentas.entities.Movimientos;
import com.movimientos.cuentas.services.MovimientoService;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimientos> getAllMovimientos() {
        return movimientoService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientoById(@PathVariable Long id) {
        return movimientoService.getMovimientoById(id);
    }

    @PostMapping
    public ResponseEntity<Movimientos> createMovimiento(@RequestBody MovimientoDto movimientoDto) {
        return movimientoService.createMovimiento(movimientoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimiento(@PathVariable Long id, @RequestBody MovimientoDto movimientoDto) {
        return movimientoService.updateMovimiento(id, movimientoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        return movimientoService.deleteMovimiento(id);
    }
}