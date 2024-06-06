package com.springwalletrod.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwalletrod.model.Transaccion;

public interface TransaccionRepository  extends JpaRepository<Transaccion,Long>{

}
