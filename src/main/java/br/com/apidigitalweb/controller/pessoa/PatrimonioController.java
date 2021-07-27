package br.com.apidigitalweb.controller.pessoa;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.PatrimonioService;

@Controller
@RequestMapping(value = "/patrimonios")
public class PatrimonioController extends BaseController<Patrimonio> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	PatrimonioService service;

	@Override
	public BaseServic<Patrimonio> getService() { 
		return service;
	}
	@GetMapping(value = "/pagetombados")
	public ResponseEntity<Page<Patrimonio>> pagetombados(@RequestParam(defaultValue = "", value = "nome") String nome,
			Pageable page) {

		Page<Patrimonio> pages = service.findallpagetombados(nome, page);

		return ResponseEntity.ok(pages);
	}
}
