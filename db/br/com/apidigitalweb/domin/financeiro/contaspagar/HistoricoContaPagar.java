package br.com.apidigitalweb.domin.financeiro.contaspagar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.Banco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class HistoricoContaPagar extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	private Banco banco;
	private long centroCusto;

	@JsonIgnore
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoContaPagar grupocontaspagar;
	
	@OneToMany(mappedBy = "historico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturaContasPagar> produtos = new ArrayList<>();

}
