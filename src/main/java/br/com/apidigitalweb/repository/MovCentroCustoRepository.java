package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.MovCentroCusto;

@Repository
public interface MovCentroCustoRepository extends JpaRepository<MovCentroCusto, Long> {

}
