package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.controller.estoque.ListaImagens;
import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.converters.UnidadeProdutoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AnuncioLoja
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class AnuncioContrato extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoFinanceiroAnuncio grupopreco;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;
	@Convert(converter = SimNaoConverter.class)
	private String status;
	private Integer saldo=0;
	private Integer saldoMinimo=0;
	private Integer saldoReserva=0;
	private Integer saldoMaximo=0;
	private double peso;
	private double largura;
	private double comprimento;
	private double altura;

	
	@JoinColumn()
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Contrato contrato;
	
	@Convert(converter = UnidadeProdutoConverter.class)
	private String unidade;

	@Transient
	private double valorInterno;

	@Transient
	private double valorFinal = 0.0;

	@Transient
	private double saldoReposicao;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<ItemProdutoAnuncio> itensProduto = new ArrayList<ItemProdutoAnuncio>();

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<DescricaoAnuncio> descricoes = new ArrayList<DescricaoAnuncio>();

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<ListaImagens> imagens = new ArrayList<ListaImagens>();

	public double getValorInterno() {
		valorInterno = 0.0;
		try {
			for (ItemProdutoAnuncio itemProdutoAnuncio : getItensProduto()) {
				valorInterno += itemProdutoAnuncio.getProduto().getValorfinal() * itemProdutoAnuncio.getQuantidade();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorInterno;
	}

	public double getValorFinal() {
		valorFinal = getValorInterno();
		try {
			valorFinal += valorFinal * getGrupopreco().getPercentualTotal() / 100;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}

	public double getSaldoReposicao() {
		saldoReposicao = 0;
		try {
			if (saldo <= saldoMinimo) {
				saldoReposicao = saldoMaximo - saldo;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saldoReposicao;
	}

}
