package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.ProdutoService;

@RestController
@RequestMapping(value = ("/produtos"))
public class ProdutoController extends BaseController<Produto> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	ProdutoService service;

	@Override
	public BaseServic<Produto> getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/*@DeleteMapping(value = "/fornecedorproduto/{id}")
	public ResponseEntity<Void> deletefornecedorproduto(@PathVariable Long id) {
		service.deletefornecedorproduto(id);
		return ResponseEntity.noContent().build();

	}*/
}
