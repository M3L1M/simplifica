package com.simplificado.test.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplificado.test.exception.ErroTransferenciaException;
import com.simplificado.test.model.entity.Transferencia;
import com.simplificado.test.model.entity.Usuario;
import com.simplificado.test.model.enums.TipoUsuario;
import com.simplificado.test.model.repository.TransferenciaRepository;
import com.simplificado.test.model.repository.UsuarioRepository;
import com.simplificado.test.service.TransferenciaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransferenciaServiceImpl implements TransferenciaService {

	@Autowired
	private TransferenciaRepository repository;
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Transferencia transferir(Transferencia transferencia) {
		Usuario usuario=transferencia.getUsuario();
		String destino=transferencia.getDestino();
		BigDecimal valor=transferencia.getValor();
		validarUsuario(usuario);validarDestino(destino);
		
		if(valor.compareTo(BigDecimal.ZERO)==0 || valor.compareTo(BigDecimal.ZERO)==-1) {
			throw new ErroTransferenciaException("Valor da transferencia invalido");
		}
		
		System.out.println(usuario.getSaldo().compareTo(valor));
		if(usuario.getSaldo().compareTo(valor)==-1) {
			throw new ErroTransferenciaException("Saldo insuficiente");
		}
		
		transferencia(usuario, destino, valor);
		transferencia.setUsuario(usuario);
		transferencia.setDestino(destino);
		transferencia.setValor(valor);
		
		return repository.save(transferencia);
	}

	@Override
	public void validarUsuario(Usuario usuario) {
		
		if(usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
			throw new ErroTransferenciaException("Lojistas não podem fazer transferencia");
		}
		
		if(usuario.getSaldo().compareTo(BigDecimal.ZERO)==0) {
			System.out.println("Entrou aqui");
		}

	}

	@Override
	public void validarDestino(String destino) {
		boolean existe=usuarioRepository.existsByCpfCnpj(destino);
		System.out.println(existe);
		if(existe==false) {
			throw new ErroTransferenciaException("CPF/CNPJ não existe na base de dados");
		}
		
		

	}

	@Override
	public List<Transferencia> listarTransferencia(Transferencia filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transferencia(Usuario usuario, String destino, BigDecimal valor) {
		Usuario destinoTransferencia=usuarioRepository.getByCpfCnpj(destino);
		
		Double val=0.0;
		val=usuario.getSaldo().doubleValue();
		val=val-valor.doubleValue();
		usuario.setSaldo(BigDecimal.valueOf(val));
		usuarioRepository.save(usuario);
		
		val=0.0;
		val=destinoTransferencia.getSaldo().doubleValue();
		val+=valor.doubleValue();
		destinoTransferencia.setSaldo(BigDecimal.valueOf(val));
		usuarioRepository.save(usuario);
	}

}
