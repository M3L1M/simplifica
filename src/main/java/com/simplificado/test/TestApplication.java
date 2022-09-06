package com.simplificado.test;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.simplificado.test.model.entity.Usuario;
import com.simplificado.test.model.enums.TipoUsuario;
import com.simplificado.test.model.repository.UsuarioRepository;
import com.simplificado.test.service.UsuarioService;

@SpringBootApplication
public class TestApplication {

	@Bean
	public CommandLineRunner commandLineRunner(
			@Autowired UsuarioRepository repository,
			@Autowired UsuarioService service) {
		return args ->{
			
			Usuario c1=new Usuario(null,TipoUsuario.COMUM,"Gabriel Corrêa Melim","05278948930","gabriel@email.com","123456",BigDecimal.valueOf(50));
			//Usuario c2=new Usuario(null,TipoUsuario.COMUM,"Gabriel Corrêa Melim","05278948930","gabriel@email.com","123456",BigDecimal.ZERO);
			
			service.validarCadastro(c1);
			service.cadastro(c1);
			
			Usuario u1=new Usuario(null,TipoUsuario.LOJISTA,"Lojinha de Games","80428368000194","lojinha@email.com","123456",BigDecimal.ZERO);
			service.validarCadastro(u1);
			service.cadastro(u1);
		
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
