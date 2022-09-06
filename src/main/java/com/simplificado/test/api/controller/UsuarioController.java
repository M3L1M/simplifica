package com.simplificado.test.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simplificado.test.api.dto.UsuarioDTO;
import com.simplificado.test.exception.ErroCadastroException;
import com.simplificado.test.exception.ErroDepositoException;
import com.simplificado.test.model.entity.Usuario;
import com.simplificado.test.model.repository.UsuarioRepository;
import com.simplificado.test.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {
	private UsuarioService service;
	private UsuarioRepository repository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity cadastrarUsuario(@RequestBody UsuarioDTO dto) {
		Usuario usuario=Usuario
				.builder()
				.tipoUsuario(dto.getTipoUsuario())
				.nomeCompleto(dto.getNomeCompleto())
				.cpfCnpj(dto.getCpfCnpj())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.saldo(dto.getSaldo()).build();
		try {
			Usuario usuarioSalvo=service.cadastro(usuario);	
			return new ResponseEntity(usuarioSalvo,HttpStatus.OK);
		}catch (ErroCadastroException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("{cpf}/{valor}")
	public ResponseEntity depositar(@PathVariable("cpf") String cpf, @PathVariable("valor") BigDecimal valor) {
		try {
			service.depositarSaldo(cpf, valor);
			return new ResponseEntity("Deposito efetuado com sucesso",HttpStatus.OK);
		}catch (ErroDepositoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	
	
	
	@GetMapping
	public List<Usuario> find(Usuario filtro){
		ExampleMatcher matcher=ExampleMatcher
				.matching()
				.withIgnoreCase(true)
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example=Example.of(filtro,matcher);
		return repository.findAll(example);
	}
	
}
