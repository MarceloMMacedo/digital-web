package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDtoReport;

@Repository
public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> {

	@Query("select distinct  new  br.com.apidigitalweb.dto.financeiro.ContasPagarDtoReport(e) from ContasPagar e  "
			+ "where e.status=?1")
	List<ContasPagarDtoReport> findAllContasPagarPagarDTO(String Status);

	@Query("select distinct  e from ContasPagar e  "
			+ "where e.status=?1")
	List<ContasPagar> findAllContasPagarPagar(String Status);
	

}
