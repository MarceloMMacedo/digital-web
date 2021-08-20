package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ResumoHistorico implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private double valor;
	public ResumoHistorico(String nome, double valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}
	
}
