package com.simplificado.test.model.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.simplificado.test.model.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tipo_usuario")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	@Column(name = "cpf_cnpj",unique = true)
	private String cpfCnpj;
	
	@Column(name = "email",unique = true)
	private String email;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "saldo",precision = 20,scale = 2)
	private BigDecimal saldo;
	
	
}
