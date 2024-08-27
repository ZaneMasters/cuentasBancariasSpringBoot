package com.movimientos.cuentas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.movimientos.cuentas.dto.ClienteDto;
import com.movimientos.cuentas.entities.Cliente;
import com.movimientos.cuentas.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public ResponseEntity<Cliente> getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return ResponseEntity.ok(cliente);
    }

    public Cliente createCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setGenero(clienteDto.getGenero());
        cliente.setEdad(clienteDto.getEdad());
        cliente.setIdentificacion(clienteDto.getIdentificacion());
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setClienteid(clienteDto.getClienteid());
        cliente.setContrasena(clienteDto.getContrasena());
        cliente.setEstado(clienteDto.isEstado());

        return clienteRepository.save(cliente);
    }

    public ResponseEntity<Cliente> updateCliente(Long id, ClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(clienteDto.getNombre());
        cliente.setGenero(clienteDto.getGenero());
        cliente.setEdad(clienteDto.getEdad());
        cliente.setIdentificacion(clienteDto.getIdentificacion());
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setClienteid(clienteDto.getClienteid());
        cliente.setContrasena(clienteDto.getContrasena());
        cliente.setEstado(clienteDto.isEstado());

        final Cliente updatedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    public ResponseEntity<Void> deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
