package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.repository.ClienteRepository;
import br.com.apidigitalweb.repository.FuncionarioRepository;

@Service
public class FuncioanarioService extends BaseServicePessoa<Funcionario> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	FuncionarioRepository repo;

	@Override
	public Page<Funcionario> findallpage(String find, Pageable page) {
		return repo.findByNomeContainingIgnoreCase(find, page);
	}

}
