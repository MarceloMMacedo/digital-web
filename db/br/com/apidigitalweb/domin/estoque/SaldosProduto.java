package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;

import lombok.Data;

@Data
public class SaldosProduto  implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Integer saldoLoja;
	private Integer saldoWeb;
	private Integer saldoContrato;
	

	private double saldoReposicaoLoja;
	private double saldoReposicaoWeb;
	private double saldoReposicaoContrato;

}
