package com.movimientos.cuentas.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movimientos.cuentas.services.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    @Autowired
    private ReporteService reportService;

    @GetMapping
    public ResponseEntity<?> getReport(@RequestParam Long clienteId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return reportService.generateReportePdf(clienteId, start, end);
    }
}
