package com.simplificado.test.model.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.simplificado.test.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	boolean existsByCpfCnpj(String cpfCnpj);

	boolean existsByEmail(String email);
	
	Usuario getByCpfCnpj(String cpf);
	
	
}
