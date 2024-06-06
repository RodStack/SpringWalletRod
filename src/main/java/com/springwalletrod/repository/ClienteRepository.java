package com.springwalletrod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springwalletrod.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByUsername(String username);
}