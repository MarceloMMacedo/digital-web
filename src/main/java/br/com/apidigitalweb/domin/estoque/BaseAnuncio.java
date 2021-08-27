package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.controller.estoque.ListaImagens;
import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.converters.UnidadeProdutoConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@MappedSuperclass
 @Getter
@Setter
@NoArgsConstructor
public class BaseAnuncio  implements Serializable, BaseEntity {

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
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	protected GrupoFinanceiroAnuncio grupopreco;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	protected Date dataVencimento;
	@Convert(converter = SimNaoConverter.class)
	protected String status;
	protected Integer saldo = 0;
	protected Integer saldoMinimo = 0;
	protected Integer saldoReserva = 0;
	protected Integer saldoMaximo = 0;
	protected double peso;
	protected double largura;
	protected double comprimento;
	protected double altura;
	protected double desconto;

	@Convert(converter = SimNaoConverter.class)
	protected String isPrecificado;
	@Convert(converter = UnidadeProdutoConverter.class)
	protected String unidade;

	@Transient
	protected double valorInterno;

	@Transient
	protected double valorFinal = 0.0;

	@Transient
	protected double saldoDisponivel;

	@Transient
	protected double saldoReposicao;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	protected List<ItemProdutoAnuncio> itensProduto = new ArrayList<ItemProdutoAnuncio>();

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	protected List<DescricaoAnuncio> descricoes = new ArrayList<DescricaoAnuncio>();

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	protected List<ListaImagens> imagens = new ArrayList<ListaImagens>();

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
			valorFinal-=valorFinal*desconto/100;
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

	public double getDesconto() {
		desconto=0;
		try{if (desconto > getGrupopreco().getPercentualDesconto())
			desconto = getGrupopreco().getPercentualDesconto();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return desconto;
	}
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseAnuncio other = (BaseAnuncio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public double getSaldoDisponivel() {
		saldoDisponivel=0;
		try {
			saldoDisponivel=getSaldo()- getSaldoReserva();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saldoDisponivel;
	}
}
