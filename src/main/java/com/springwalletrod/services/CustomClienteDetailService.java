package com.springwalletrod.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springwalletrod.model.Cliente;
import com.springwalletrod.repository.ClienteRepository;

@Service
public class CustomClienteDetailService implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente =  clienteRepository.findByUsername(username);
		
		if(cliente == null) {
			throw new UsernameNotFoundException("usuario no encontrado");
		}
		
		Set<SimpleGrantedAuthority> authorities = cliente.getRoles().stream().map(
				role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(cliente.getUsername(), cliente.getPassword(), authorities);
	}

	
}