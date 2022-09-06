package com.simplificado.test.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplificado.test.model.entity.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {
	
}
