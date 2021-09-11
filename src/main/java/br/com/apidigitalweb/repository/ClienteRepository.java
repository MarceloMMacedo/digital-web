package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.pessoa.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {



	Page<Cliente> findByNomeContainingIgnoreCase(String nome,Pageable page);

	List<Cliente> findByNomeContainingIgnoreCase(String nome );
	
}
