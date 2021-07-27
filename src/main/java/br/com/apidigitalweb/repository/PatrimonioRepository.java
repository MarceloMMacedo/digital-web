package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.Produto;

@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long>  {
	// @Query("select e from Patrimonio e where e.status=?1")
	Page<Patrimonio> findByStatusAndNomeContainingIgnoreCaseOrNomeIsNull(String status, String nome, Pageable page);
}
