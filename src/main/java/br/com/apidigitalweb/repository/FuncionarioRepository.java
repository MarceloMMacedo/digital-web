package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.pessoa.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	Funcionario	findByEmailAndStatus(String email, String status );

	Page<Funcionario> findByNomeContainingIgnoreCase(String nome,Pageable page);
}
