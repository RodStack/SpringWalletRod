package com.springwalletrod.controllers;


import java.util.List;

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

import com.springwalletrod.dto.CuentaDTO;
import com.springwalletrod.model.Cliente;
import com.springwalletrod.model.Cuenta;
import com.springwalletrod.model.Transaccion;
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
		
		List<Cliente> listaClientes = clienteService.obtenerListaClientes();
        model.addAttribute("clientes", listaClientes);
		
        List<Transaccion> listaTransacciones =  clienteService.obtenerTodasTransacciones();  
		model.addAttribute("transacciones", listaTransacciones);
        
        Cuenta cuenta = clienteService.findByUsername(usuarioActual).getCuenta();
		CuentaDTO cuentaDTO = new CuentaDTO(cuenta.getNumeroCuenta(), cuenta.getSaldoActual());
		model.addAttribute("vistaCuenta",cuentaDTO);
		return "cuentaTemplate"; // el nombre del html que se va a abrir
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/depositar")
	public String depositar(@RequestParam Double montoDepositar, Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
		Cliente cliente = clienteService.findByUsername(usuarioActual);
		Integer idCliente = cliente.getId();
	    clienteService.depositarFondos(idCliente, montoDepositar);
        Cuenta cuenta = cliente.getCuenta();
		model.addAttribute("vistaCuenta",cuenta);
		return "redirect:/cuenta";
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/retirar")
	public String retirar(@RequestParam Double montoRetiro, Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
		Cliente cliente = clienteService.findByUsername(usuarioActual);
		Integer idCliente = cliente.getId();
		clienteService.retirarFondos(idCliente, montoRetiro);
        Cuenta cuenta = cliente.getCuenta();
		model.addAttribute("vistaCuenta",cuenta);
		return "redirect:/cuenta";
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/transferir")
	public String transferirEntreCuentas(@RequestParam Integer idClienteDestino, @RequestParam Double montoTransferir, Model model) {
		String usuarioActual = obtenerUsuarioLogueado();
		Cliente cliente = clienteService.findByUsername(usuarioActual);
		Integer idClienteOrigen = cliente.getId();
	    clienteService.transferirEntreCuentes(idClienteOrigen, idClienteDestino, montoTransferir);
	    return "redirect:/cuenta";
	}
	
	private String obtenerUsuarioLogueado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String usuarioActual = authentication.getName();
		return usuarioActual;
	}

}