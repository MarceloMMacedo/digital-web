package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.Modelo;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.ModeloService;

@RestController
@RequestMapping(value = ("/modelos"))
public class ModeloController extends BaseController<Modelo> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ModeloService service;
	@Override
	public BaseServic<Modelo> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
