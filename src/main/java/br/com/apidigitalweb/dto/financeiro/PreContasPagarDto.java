package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;

import javax.persistence.Convert;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto;

public class PreContasPagarDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private CotacaoDto cotacao;

	private String status;

	public PreContasPagarDto(PreContasPagar c) {
		cotacao = new CotacaoDto(c.getCotacao());
		status = c.getCotacao().getStatus();
	}
}
