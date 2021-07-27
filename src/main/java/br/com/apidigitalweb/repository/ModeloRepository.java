package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long>  {
}
