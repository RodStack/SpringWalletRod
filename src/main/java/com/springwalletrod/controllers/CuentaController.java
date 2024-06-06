package com.springwalletrod.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springwalletrod.model.Cliente;
import com.springwalletrod.model.Cuenta;
import com.springwalletrod.services.ClienteService;


@Controller
@RequestMapping("/cuenta")
public class CuentaController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping
	public String verCuenta(Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
		model.addAttribute("username", usuarioActual);
		
		Cuenta cuentaDTO = clienteService.findByUsername(usuarioActual).getCuenta();
		model.addAttribute("vistaCuenta",cuentaDTO);
		return "cuentaTemplate"; // el nombre del html que se va a abrir
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/depositar")
	public String depositar(@RequestParam Double montoDepositar, @RequestParam Integer idCliente, Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
		Cliente cliente = clienteService.findByUsername(usuarioActual);
	    clienteService.retirarFondos(idCliente, montoDepositar);
        Cuenta cuenta = cliente.getCuenta();
		model.addAttribute("vistaCuenta",cuenta);
		return "cuentaTemplate";
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/retirar")
	public String retirar(@RequestParam Double montoRetiro, @RequestParam Integer idCliente, Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
	    Cliente cliente = clienteService.findByUsername(usuarioActual);
	    clienteService.retirarFondos(idCliente, montoRetiro);
        Cuenta cuenta = cliente.getCuenta();
		model.addAttribute("vistaCuenta",cuenta);
		return "cuentaTemplate";
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/transferir")
	public String transferirEntreCuentas(@RequestParam Integer idClienteDestino, @RequestParam Double montoTransferir, Model model) {
	    String usuarioActual = obtenerUsuarioLogueado();
	    Cliente clienteOrigen = clienteService.findByUsername(usuarioActual);
	    Integer idClienteOrigen = clienteOrigen.getId();
	    clienteService.transferirEntreCuentes(idClienteOrigen, idClienteDestino, montoTransferir);
		return "cuentaTemplate";
	}
	
	private String obtenerUsuarioLogueado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String usuarioActual = authentication.getName();
		return usuarioActual;
	}

}