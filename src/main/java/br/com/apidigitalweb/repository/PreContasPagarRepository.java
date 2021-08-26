package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;

@Repository
public interface PreContasPagarRepository extends JpaRepository<PreContasPagar, Long> {
 List<PreContasPagar> findByStatus(String status);
}
