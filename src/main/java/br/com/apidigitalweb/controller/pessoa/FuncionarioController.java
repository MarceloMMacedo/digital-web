package br.com.apidigitalweb.controller.pessoa;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.apidigitalweb.controller.BaseControllerPessoa;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.service.BaseServicePessoa;
import br.com.apidigitalweb.service.FuncioanarioService;

@Controller
@RequestMapping(value = "/funcionarios")
public class FuncionarioController extends BaseControllerPessoa<Funcionario> implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Autowired
	FuncioanarioService serviceFuncionarioService;

	 @Override
	public BaseServicePessoa<Funcionario> getService() { 
		return serviceFuncionarioService;
	}

	
}
