package br.com.apidigitalweb.service;

import java.io.Serializable;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.repository.ClienteRepository;

@Service
public class ClienteService  extends BaseServicePessoa<Cliente> implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	 
	@Autowired
	ClienteRepository repo;
	
	
	
	@Override
	public Page<Cliente> findallpage(String find, Pageable page) {
		// TODO Auto-generated method stub
		return repo.findByNomeContainingIgnoreCase(find, page);
	}

}
