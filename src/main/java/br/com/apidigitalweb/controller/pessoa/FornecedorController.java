package br.com.apidigitalweb.controller.pessoa;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.cloud.BaseService;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.controller.BaseControllerPessoa;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.BaseServicePessoa;
import br.com.apidigitalweb.service.FornecedorService;
import br.com.apidigitalweb.service.ProdutoService;

@Controller
@RequestMapping(value = "/fornecedores")
public class FornecedorController extends BaseControllerPessoa<Fornecedor> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	FornecedorService serviceFornecedorService;

	 @Override
	public BaseServicePessoa<Fornecedor> getService() { 
		return serviceFornecedorService;
	}

	
}
