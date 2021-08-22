package br.com.apidigitalweb.domin.cotacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cotacao extends BaseDomain implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	private Date dataAbertura;
	private Date dataFim;
	private Fornecedor fornecedor;

	@Convert(converter = StatusConverter.class)
	private String status;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<ItensCotacao> itensCotacaos = new ArrayList<ItensCotacao>();

	@Transient
	private double total;

	private String tipoFrete;
	private double valorFrete;

}
