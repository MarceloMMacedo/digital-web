package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.cotacao.Cotacao;
@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
	@Query("SELECT e FROM Cotacao e WHERE e.status='Aberto' or e.status='Andamento' or e.status='Aprovado'  or e.status='Conferência'  ")
	Page<Cotacao> listAllActive(Pageable page);

}
