package br.com.apidigitalweb.domin.cotacao;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cotacao   implements Serializable, BaseEntity {
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;

	@Transient
	protected String imagemView;
	private Date dataAbertura;
	private Date dataFim;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;

	@Convert(converter = StatusConverter.class)
	private String status;

	 @ElementCollection 
	 @CollectionTable( joinColumns = @JoinColumn(name = "id") )  
	private List<ItensCotacao> itensCotacaos = new LinkedList<>();

	@Transient
	private double total;

	private String tipoFrete;
	private double valorFrete;
	public double getTotal() {
		total = 0;
		try {
for (ItensCotacao itensCotacao : getItensCotacaos()) {
	total+=itensCotacao.getTotal();
}
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
}
