package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.dto.ordem.OrdemDto;
@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

	//@Query("select new br.com.apidigitalweb.dto.ordem.OrdemDto(e) from OrdemServico e where e.status=?1 and e.cliente.nome like ?2")
	List<OrdemServico> findAllByStatusAndClienteNomeContainingIgnoreCase(String status,String nome);
}
