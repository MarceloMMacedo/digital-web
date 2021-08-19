package br.com.apidigitalweb.dto.contrato;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Medidores;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ItensContratoPatrimonioDTO implements Serializable {

	@Getter
	@Setter
	@NoArgsConstructor
	public class Patrimo extends BaseDomain implements Serializable {
		private static final long serialVersionUID = 1L;
		private List<Medidores> medidores=new LinkedList<>();
		
		public Patrimo(Patrimonio p) {
			super();
			this.medidores = p.getMedidores();
			setId(p.getId());
			setNome(p.getNome());
		}
			
	}

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Patrimo patrimonio = new Patrimo();
	private double valorUnitarioFranquia;
	private String isFranquado;
	private int franquia;
	private double valorFranquia;
	private double valorPreDeterminado;
	private double valorFinal;
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();

	public ItensContratoPatrimonioDTO(ItensContratoPatrimonio i) {
		super();
		this.id = i.getId();
		this.nome = i.getNome();
		patrimonio=new Patrimo(i.getPatrimonio());
		this.valorUnitarioFranquia = i.getValorUnitarioFranquia();
		this.isFranquado = i.getIsFranquado();
		this.franquia = i.getFranquia();
		this.valorFranquia = i.getValorFranquia();
		this.valorPreDeterminado = i.getValorPreDeterminado();
		this.valorFinal = i.getValorFinal();
		this.endereco = i.getEndereco();
		this.contato = i.getContato();
	}
	/*
	 * public void setPatrimonio(Patrimonio p) { patrimonio=new
	 * BaseDto(p,p.getImagemView()); }
	 */

}
