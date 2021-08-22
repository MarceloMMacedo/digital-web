package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.CotacaoService;

@RestController
@RequestMapping(value = ("/cotacaos"))
public class CotacaoController extends BaseController<Cotacao> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CotacaoService service;

	@Override
	public BaseServic<Cotacao> getService() {
		return service;
	}

	@GetMapping(value = "/cotacao/{id}")
	public ResponseEntity<CotacaoDto> getcotacao(@PathVariable Long id) {
		return ResponseEntity.ok(service.getCotacao(id));
	}

}
