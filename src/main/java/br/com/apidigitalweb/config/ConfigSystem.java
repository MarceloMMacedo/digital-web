package br.com.apidigitalweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.apidigitalweb.domin.pessoa.Empresa;
@Configuration
public class ConfigSystem {
	@Bean
	public Empresa empresa() {
		 
		return Empresa.getEmpresa();
	}
}
