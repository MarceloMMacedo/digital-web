package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.estoque.Modelo;
import br.com.apidigitalweb.repository.ModeloRepository;

@Service
public class ModeloService extends BaseServic<Modelo>  implements Serializable {
	private static final long serialVersionUID = 1L;

	 
}
