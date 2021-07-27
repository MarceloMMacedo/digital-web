 package br.com.apidigitalweb.controller.pessoa;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.apidigitalweb.controller.BaseControllerPessoa;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.service.BaseServicePessoa;
import br.com.apidigitalweb.service.ClienteService;

@Controller
@RequestMapping(value = "/clientes")
public class ClienteController extends BaseControllerPessoa<Cliente> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	ClienteService serviceClienteService;

	 @Override
	public BaseServicePessoa<Cliente> getService() { 
		return serviceClienteService;
	}

	
}
