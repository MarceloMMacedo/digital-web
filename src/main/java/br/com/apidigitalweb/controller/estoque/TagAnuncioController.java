package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.TagAnuncio;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.TagAnuncioService;

@RestController
@RequestMapping(value = ("/taganuncios"))
public class TagAnuncioController extends BaseController<TagAnuncio> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	TagAnuncioService service;
	@Override
	public BaseServic<TagAnuncio> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
