package com.springwalletrod.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springwalletrod.model.Cliente;
import com.springwalletrod.model.Cuenta;
import com.springwalletrod.model.Rol;
import com.springwalletrod.repository.ClienteRepository;
import com.springwalletrod.repository.CuentaRepository;
import com.springwalletrod.repository.RolRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CuentaService {
	@Autowired
	private CuentaRepository cuentaRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private RolRepository rolRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostConstruct
	public void init() {		
		//crearemos los roles
		Rol adminRol = new Rol();
		adminRol.setName("ROLE_ADMIN");
		rolRepository.save(adminRol);
				
		Rol userRol = new Rol();
		userRol.setName("ROLE_USER");
		rolRepository.save(userRol);
		
		
		Cuenta cuenta = new Cuenta();
		cuenta.setSaldoActual(10000.0);		
		cuenta.setNumeroCuenta(1);
		
		
		Cliente cliente = new Cliente();
		cliente.setUsername("Rod");
		cliente.setPassword(passwordEncoder.encode("admin"));
		cliente.setCuenta(cuenta);
		Set<Rol> userRoles = new HashSet<>();
		userRoles.add(userRol);
		cliente.setRoles(userRoles);
		clienteRepository.save(cliente);
		cuentaRepository.save(cuenta);
		
		Cuenta cuenta2 = new Cuenta();
		cuenta2.setSaldoActual(10000.0);		
		cuenta2.setNumeroCuenta(2);
		
		
		Cliente cliente2 = new Cliente();
		cliente2.setUsername("Luciana");
		cliente2.setPassword(passwordEncoder.encode("admin"));
		cliente.setCuenta(cuenta2);
		userRoles.add(userRol);
		cliente2.setRoles(userRoles);
		clienteRepository.save(cliente);
		cliente2.setCuenta(cuenta);
		cuentaRepository.save(cuenta2);
	}
	
}
