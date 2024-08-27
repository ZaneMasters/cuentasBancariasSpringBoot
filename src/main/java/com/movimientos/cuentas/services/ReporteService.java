package com.movimientos.cuentas.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.movimientos.cuentas.entities.Cuenta;
import com.movimientos.cuentas.entities.Movimientos;
import com.movimientos.cuentas.repositories.CuentaRepository;
import com.movimientos.cuentas.repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    public ResponseEntity<byte[]> generateReportePdf(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Fetch the data for the report
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        // No es necesario parsear las fechas de nuevo si ya son LocalDate
        List<Movimientos> movimientos = movimientosRepository.findByCuentaAndFechaBetween(
                cuentas.get(0),
                fechaInicio.atStartOfDay(),
                fechaFin.atTime(23, 59)
        );

        // Create a PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add title
        document.add(new Paragraph("Reporte de Estado de Cuenta").setBold().setFontSize(14));

        // Add client information
        document.add(new Paragraph("Cliente: " + cuentas.get(0).getCliente().getNombre()));
        document.add(new Paragraph("Identificación: " + cuentas.get(0).getCliente().getIdentificacion()));
        document.add(new Paragraph("Fecha: " + fechaInicio + " - " + fechaFin));

        // Add table header
        Table table = new Table(4);
        table.addHeaderCell("Fecha");
        table.addHeaderCell("Tipo Movimiento");
        table.addHeaderCell("Valor");
        table.addHeaderCell("Saldo");

        // Add rows to the table
        for (Movimientos movimiento : movimientos) {
            table.addCell(movimiento.getFecha().toString());
            table.addCell(movimiento.getTipoMovimiento());
            table.addCell(String.valueOf(movimiento.getValor()));
            table.addCell(String.valueOf(movimiento.getSaldo()));
        }

        document.add(table);

        // Close the document
        document.close();

        // Return the PDF as a byte array
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte.pdf")
                .body(baos.toByteArray());
    }
}
