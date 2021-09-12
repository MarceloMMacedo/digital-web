package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemcontrato.OrdemContrato;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
@Repository
public interface OrdemContratoRepository extends JpaRepository<OrdemContrato, Long> {

}
