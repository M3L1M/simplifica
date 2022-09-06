package com.simplificado.test.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.simplificado.test.model.entity.Usuario;

public interface UsuarioService {
	
	
	Usuario cadastro(Usuario usuario);
	void validarCadastro(Usuario usuario);
	void validarCpfCnpj(Usuario usuario);
	
	void depositarSaldo(String cpfCnpj,BigDecimal valor);
	void validaDeposito(BigDecimal valor);
	
	void transferencia(String cpfOrigem,String cpfDestino,BigDecimal valor);
	void validarTransferencia(String cpf);
	
	Optional<Usuario> obterPorId(Integer id);
	
}
