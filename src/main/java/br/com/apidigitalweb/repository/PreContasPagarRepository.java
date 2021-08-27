package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;

@Repository
public interface PreContasPagarRepository extends JpaRepository<PreContasPagar, Long> {
 Page<PreContasPagar> findAllByStatus(String status,Pageable page);
}
