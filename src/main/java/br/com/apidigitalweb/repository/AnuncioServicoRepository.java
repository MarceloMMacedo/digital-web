package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;

@Repository

public interface AnuncioServicoRepository extends JpaRepository<AnuncioServico, Long> {
	Page<AnuncioServico> findByNomeContainingIgnoreCase(String nome,Pageable page);
 
}
