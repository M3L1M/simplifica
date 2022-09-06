package com.simplificado.test.service;

import java.math.BigDecimal;
import java.util.List;

import com.simplificado.test.model.entity.Transferencia;
import com.simplificado.test.model.entity.Usuario;

public interface TransferenciaService {
	
	
	public Transferencia transferir(Transferencia transferencia);
	
	public void validarUsuario(Usuario usuario);
	public void validarDestino(String destino);
	
	public void transferencia(Usuario usuario,String destino,BigDecimal valor);
	
	public List<Transferencia> listarTransferencia(Transferencia filtros);
}
