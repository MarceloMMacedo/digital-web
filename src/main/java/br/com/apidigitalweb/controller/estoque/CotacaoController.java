package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto;
import br.com.apidigitalweb.dto.reposicao.ListCotacaoDto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.CotacaoService;

@RestController
@RequestMapping(value = ("/cotacoes"))
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
	@GetMapping(value = "/ressuprir/{id}")
	public ResponseEntity<Void> ressuprir(@PathVariable Long id){
		
		service.ressuprir(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value = "/getanuncios/{id}")
	public ResponseEntity<List<SampleDto>> getanuncios(@PathVariable String id) {
		return ResponseEntity.ok(service.anuncios(id));
	}
	
	@RequestMapping(value = "/addanuncio",method = RequestMethod.GET)
	public ResponseEntity<Void> addanuncio(@RequestParam  Long idcotacao,
			@RequestParam  Long idanuncio,
			@RequestParam  Integer qtd,
			@RequestParam  String tipo) {
		service.addanuncio(idcotacao, idanuncio, qtd, tipo);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping(value = "/findallpagecotacaodto")
	public ResponseEntity<Page<ListCotacaoDto>> findallpagecotacaodto( @RequestParam(defaultValue = "", value = "nome") String nome,
			Pageable page) {
		Page<ListCotacaoDto> pages = service .findallpagecotacaodto( page);
		return ResponseEntity.ok(pages);
	}
}
