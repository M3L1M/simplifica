package com.simplificado.test.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplificado.test.exception.ErroCadastroException;
import com.simplificado.test.exception.ErroDepositoException;
import com.simplificado.test.exception.ErroTransferenciaException;
import com.simplificado.test.model.entity.Usuario;
import com.simplificado.test.model.enums.TipoUsuario;
import com.simplificado.test.model.functions.CriptografaSenha;
import com.simplificado.test.model.functions.Validacoes;
import com.simplificado.test.model.repository.UsuarioRepository;
import com.simplificado.test.service.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	@Transactional
	public Usuario cadastro(Usuario usuario) {
		validarCadastro(usuario);
		return repository.save(usuario);
	}

	@Override
	public void validarCadastro(Usuario usuario) {
		
		if(usuario.getTipoUsuario()==null || usuario.getNomeCompleto().trim().equals("")) {
			throw new ErroCadastroException("Tipo do usuario em branco");
		}
		
		
		
		if(usuario.getNomeCompleto()==null || usuario.getNomeCompleto().trim().equals("")) {
			throw new ErroCadastroException("Nome de usuario em branco");
		}
		
		validarCpfCnpj(usuario);
		
		if(usuario.getEmail()==null || usuario.getEmail().trim().equals("")) {
			throw new ErroCadastroException("Email em branco");
		}
		
		boolean existe=repository.existsByEmail(usuario.getEmail());
		
		if(existe==true) {
			throw new ErroCadastroException("Email ja foi cadastrado");
		}
		
		
		if(usuario.getSenha()==null || usuario.getSenha().trim().equals("")) {
			throw new ErroCadastroException("Senha em branco");
		}
		String senhaCriptografada=CriptografaSenha.cryptography(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		
		

	}
	
	@Override
	public void validarCpfCnpj(Usuario usuario) {
		Validacoes validacoes=new Validacoes();
		
		if(usuario.getCpfCnpj()==null || usuario.getCpfCnpj().trim().equals("")) {
			if(usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
				throw new ErroCadastroException("CNPJ em branco");
			}if(usuario.getTipoUsuario().equals(TipoUsuario.COMUM))  {
				throw new ErroCadastroException("CPF em branco");
			}
		}
		
		if(usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
			boolean valido=validacoes.isCNPJ(usuario.getCpfCnpj());
			if(valido==false) {
				throw new ErroCadastroException("CNPJ invalido");
			}
		}if(usuario.getTipoUsuario().equals(TipoUsuario.COMUM))  {
			boolean valido=validacoes.isCPF(usuario.getCpfCnpj());
			if(valido==false) {
				throw new ErroCadastroException("CPF invalido");
			}
		}
		
		boolean existe=repository.existsByCpfCnpj(usuario.getCpfCnpj());
		
		if(existe==true && usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
			throw new ErroCadastroException("CNPJ ja cadastrado");
		}
		if(existe==true && usuario.getTipoUsuario().equals(TipoUsuario.COMUM))  {
			throw new ErroCadastroException("CPF ja cadastrado");
		}
		
	}
	

	
	@Override
	public void depositarSaldo(String cpfCnpj, BigDecimal valor) {
		validaDeposito(valor);
		boolean existe=repository.existsByCpfCnpj(cpfCnpj);
		
		if(existe==false) {
			throw new ErroDepositoException("CPF invalido");
		}
		
		Usuario usuario=repository.getByCpfCnpj(cpfCnpj);
		Double v=0.0;
		
		v=usuario.getSaldo().doubleValue();
		v+=valor.doubleValue();
		
		usuario.setSaldo(BigDecimal.valueOf(v));
		System.out.println(usuario.getSaldo());
		repository.save(usuario);
		
	}

	@Override
	public void validaDeposito(BigDecimal valor) {
		if(valor.compareTo(BigDecimal.ZERO)==0 || valor.compareTo(BigDecimal.ZERO)==-1) {
			throw new ErroDepositoException("Valor invalido");
		}
		
	}

	@Override
	public void transferencia(String cpfOrigem, String cpfDestino, BigDecimal valor) {
		validaDeposito(valor);
		
		
	}

	@Override
	public void validarTransferencia(String cpf) {
		boolean existe=repository.existsByCpfCnpj(cpf);
		
		
		
	}

	@Override
	public Optional<Usuario> obterPorId(Integer id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	
	

	

}
