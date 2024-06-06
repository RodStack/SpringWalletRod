package com.springwalletrod.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaccion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime fecha;
	private String tipo;
	private double monto;
	private Integer cuentaOrigen;
	private Integer cuentaDestino;
	
	public Transaccion(LocalDateTime fecha, String tipo, double monto, Integer cuentaOrigen, Integer cuentaDestino) {
		this.fecha = fecha;
		this.tipo = tipo;
		this.monto = monto;
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
	}
	
	public Transaccion() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Integer getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(Integer cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public Integer getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(Integer cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	
}
