package com.movimientos.cuentas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movimientos.cuentas.dto.CuentaDto;
import com.movimientos.cuentas.entities.Cuenta;
import com.movimientos.cuentas.services.CuentaService;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        return cuentaService.getCuentaById(id);
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody CuentaDto cuentaDto) {
        return cuentaService.createCuenta(cuentaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody CuentaDto cuentaDto) {
        return cuentaService.updateCuenta(id, cuentaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        return cuentaService.deleteCuenta(id);
    }
}