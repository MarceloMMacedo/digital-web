package br.com.apidigitalweb.dto.contrato;

import java.io.Serializable;

import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.BaseDto;
import lombok.Data;

@Data
public class ItensContratoPatrimonioDTO implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;	
	private BaseDto patrimonio=new BaseDto();	
	private double valorUnitarioFranquia; 
	private String isFranquado;
	private int franquia;
	private double valorFranquia;
	private double valorPreDeterminado;
	private double valorFinal;
	private Endereco endereco = new Endereco(); 
	private Contato contato = new Contato();
	
	
	public ItensContratoPatrimonioDTO( ItensContratoPatrimonio i) {
		super();
		this.id = i.getId();
		this.nome = i.getNome();
		setPatrimonio(i.getPatrimonio());
		this.valorUnitarioFranquia = i.getValorUnitarioFranquia();
		this.isFranquado = i.getIsFranquado();
		this.franquia = i.getFranquia();
		this.valorFranquia = i.getValorFranquia();
		this.valorPreDeterminado = i.getValorPreDeterminado();
		this.valorFinal = i.getValorFinal();
		this.endereco = i.getEndereco();
		this.contato = i.getContato();
	}
	
	public void setPatrimonio(Patrimonio p) {
		 patrimonio=new BaseDto(p,p.getImagemView());
	}
	
	


}
