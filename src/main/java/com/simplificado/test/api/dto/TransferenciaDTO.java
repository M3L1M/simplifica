package com.simplificado.test.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDTO {
	private Integer id;
	private Integer idUsuario;
	private String destino;
	private BigDecimal valor;
}
