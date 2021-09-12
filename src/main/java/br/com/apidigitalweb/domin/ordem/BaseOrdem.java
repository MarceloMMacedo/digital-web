package br.com.apidigitalweb.domin.ordem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.CalnalOrdemConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordemvendaloja.ItensMaterialInVendaLoja;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.EquipamentoCliente;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.ordem.ItensInsOrdemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseOrdem  implements BaseEntity, Serializable {


	protected static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;

	@Transient
	protected String imagemView;
	
	/**
	 * 
	 * 0-WEB 1-ONSITE 2-TELEFONE 4-WHATSAPP 5-OUTROS
	 */
	@Convert(converter = CalnalOrdemConverter.class)
	protected String canal;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	protected Cliente cliente;

	
	protected Date dataAbertura;

	
	protected Date dataProgramada;

	
	protected Date dataConclusao;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	protected Funcionario vendedor;
	


	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected EquipamentoCliente equipamento;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	protected Funcionario tecnico;

	protected String garantia;
	
	/*@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	protected List<ItensMaterialInVendaLoja> itensOrdemVenda=new ArrayList<>();
*/
	protected String setorentrega;
	
 
	
	/**
	 * 
	 * Aberto Concluido Aguardandoproduto
	 */
	@Convert(converter = StatusConverter.class)
	protected String status;
	
	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Endereco endereco = new Endereco();

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Contato contato = new Contato();

	protected String formaEntrega;
	protected double valorEntrega;


	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "banco", column = @Column(name = "bancoOS")),
		@AttributeOverride(name = "faturavel", column = @Column(name = "faturavelOS")),
		@AttributeOverride(name = "datavencimento", column = @Column(name = "datavencimentoOS")),
		@AttributeOverride(name = "parcelas", column = @Column(name = "parcelasOS")) })
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	protected FinanceiroOrdem financeiroOrdem = new FinanceiroOrdem();
	
	@Transient
	protected List<ItensInsOrdemDto> itensInsOrdemDtos;
	
	@Transient
	protected double totalItensMaoObra;

	@Transient
	protected double totalItensMaterial;

	@Transient
	protected double total;
	public List<ItensInsOrdemDto> getItensInsOrdemDtos() {
		return itensInsOrdemDtos;
	}

}
