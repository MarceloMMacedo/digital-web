package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.SampleDto;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	Funcionario	findByEmailAndStatus(String email, String status );

	Page<Funcionario> findByNomeContainingIgnoreCase(String nome,Pageable page);
	
	@Query("Select new br.com.apidigitalweb.dto.SampleDto(e) from Funcionario e where e.funcao='Técnico'")
	List<SampleDto> lisAllTecnicoSampleDto();
}
