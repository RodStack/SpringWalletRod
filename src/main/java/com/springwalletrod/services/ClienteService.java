package com.springwalletrod.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springwalletrod.model.Cliente;
import com.springwalletrod.model.Transaccion;
import com.springwalletrod.repository.ClienteRepository;
import com.springwalletrod.repository.TransaccionRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TransaccionRepository transaccionRepository;
	
	public List<Cliente> obtenerListaClientes(){
		return clienteRepository.findAll();
	}
	
	public Optional<Cliente> obtenerClienteById(Integer id){
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente;
	}
	
	public Cliente guardar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void eliminar(Integer id) {
		clienteRepository.deleteById(id);
	}
	
	public void depositarFondos(Integer idCliente, double monto) {
		LocalDateTime fecha;	
		
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		cliente.getCuenta().depositar(monto);
		clienteRepository.save(cliente);
		
		Transaccion transaccion = new Transaccion(LocalDateTime.now(), "Deposito", monto, cliente.getCuenta().getId(), null);
		transaccionRepository.save(transaccion);
	}
	
	
	public void retirarFondos(Integer idCliente, double monto) {
		
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		cliente.getCuenta().retirar(monto);
		clienteRepository.save(cliente);
		
		Transaccion transaccion = new Transaccion(LocalDateTime.now(), "Retiro", monto, cliente.getCuenta().getId(), null);
		transaccionRepository.save(transaccion);
	}
	
	public boolean transferirEntreCuentes(Integer idClienteOrigen, Integer idCLienteDestino, double monto) {
		Cliente clienteOrigen = clienteRepository.findById(idClienteOrigen).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		Cliente clienteDestino = clienteRepository.findById(idCLienteDestino).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		if(clienteOrigen.getCuenta().getSaldoActual() >= monto) {
			clienteOrigen.getCuenta().retirar(monto);
			clienteDestino.getCuenta().depositar(monto);
			
			clienteRepository.save(clienteOrigen);
			clienteRepository.save(clienteDestino);
			Transaccion transaccion = new Transaccion(LocalDateTime.now(), "Retiro", monto, clienteOrigen.getCuenta().getId(), clienteDestino.getCuenta().getId());
			transaccionRepository.save(transaccion);
			return true;
		}
		return false;
	}
	public Cliente findByUsername(String username) {
	    return clienteRepository.findByUsername(username);
	}
	
	public List<Transaccion> obtenerTodasTransacciones(){
		return transaccionRepository.findAll();
	}
	
}