package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;

@Repository

public interface AnuncioContratoRepository extends JpaRepository<AnuncioContrato, Long> {
	Page<AnuncioContrato> findByNomeContainingIgnoreCase(String nome,Pageable page);
}
