package br.com.apidigitalweb.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import lombok.Data;
@Data
public class AnuncioLojaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
 
	private Produto produto ;

 
	private GrupoFinanceiroAnuncio grupoPreco ;
 
 
	List<String> tags = new ArrayList<>();

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private LocalDate vencimento;

	private Integer saldo;
	private Integer saldoMinimo;
	private Integer saldoReserva;
	private String descricao;
 
	private String status;


}
