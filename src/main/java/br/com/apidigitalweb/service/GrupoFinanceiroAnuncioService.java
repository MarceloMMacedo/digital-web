package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.repository.GrupoFinanceiroAnuncioRepository;

@Service
public class GrupoFinanceiroAnuncioService extends BaseServic<GrupoFinanceiroAnuncio>  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	GrupoFinanceiroAnuncioRepository repo  ;
	
	 

}
