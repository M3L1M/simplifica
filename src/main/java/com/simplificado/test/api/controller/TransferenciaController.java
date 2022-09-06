package com.simplificado.test.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplificado.test.api.dto.TransferenciaDTO;
import com.simplificado.test.exception.ErroTransferenciaException;
import com.simplificado.test.model.entity.Transferencia;
import com.simplificado.test.model.entity.Usuario;
import com.simplificado.test.service.TransferenciaService;
import com.simplificado.test.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/transferencia")
@AllArgsConstructor
public class TransferenciaController {
	private TransferenciaService service;
	private UsuarioService usuarioService;
	
	
	@PostMapping
	public ResponseEntity transferir(@RequestBody TransferenciaDTO dto) {
		try {
			Transferencia entity=converter(dto);
			entity=service.transferir(entity);
			return new ResponseEntity(entity,HttpStatus.CREATED);
		}catch (ErroTransferenciaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private Transferencia converter(TransferenciaDTO dto) {
		Transferencia transferencia=new Transferencia();
		
		transferencia.setId(dto.getId());
		transferencia.setDestino(dto.getDestino());
		transferencia.setValor(dto.getValor());
		
		Usuario usuario=usuarioService.obterPorId(dto.getIdUsuario())
						.orElseThrow(()->new ErroTransferenciaException("Usuario não encontrado"));
		transferencia.setUsuario(usuario);
		
		return transferencia;
	}
}
