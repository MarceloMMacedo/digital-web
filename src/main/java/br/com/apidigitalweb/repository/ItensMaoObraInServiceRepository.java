package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemservico.ItensMaoObraInService;

@Repository
public interface ItensMaoObraInServiceRepository extends JpaRepository<ItensMaoObraInService, Long> {

}
