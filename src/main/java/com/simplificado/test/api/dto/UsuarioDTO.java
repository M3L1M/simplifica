package com.simplificado.test.api.dto;

import java.math.BigDecimal;

import com.simplificado.test.model.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
	private TipoUsuario tipoUsuario;
	private String nomeCompleto;
	private String cpfCnpj;
	private String email;
	private String senha;
	private BigDecimal saldo;
}
