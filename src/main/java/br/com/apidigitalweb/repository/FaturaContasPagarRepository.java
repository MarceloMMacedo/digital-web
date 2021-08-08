package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;

@Repository
public interface FaturaContasPagarRepository extends JpaRepository<FaturaContasPagar, Long> {

	List<FaturaContasPagar> findAllByCentroCustoAndStatus(CentroCusto centroCusto, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContasPagar e where e.centroCusto=?1 and e.status=?2")
	double totalAbertoByCentroCusto(CentroCusto centroCusto, String status);

	List<FaturaContasPagar> findByFornecedorAndStatus(Fornecedor fornecedor, String status);

	List<FaturaContasPagar> findAllByBancoAndStatus(Banco banco, String status);

	List<FaturaContasPagar> findAllByHistoricoAndStatus(HistoricoContaPagar historico, String status);

	List<FaturaContasPagar> findByStatus(String status);
	
	

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaContasPagar f where d f.status=?1 ordet by f.dataVencimento" )
	List<FaturasDto> findByStatusDTO(String status);


	List<FaturaContasPagar> findByContasPagarAndStatus(ContasPagar contaspagar, String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaContasPagar f where f.banco=?1 and f.status=?2")
	List<FaturasDto> allBanco( Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContasPagar e where e.fornecedor=?1 and e.status=?2")
	double totalAbertoByFornecedor(Fornecedor fornecedor, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContasPagar e where e.contasPagar=?1 and e.status=?2")
	double totalAbertoByContasPagar(ContasPagar contasPagar, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContasPagar e where e.banco=?1 and e.status=?2")
	double totalAbertoByBanco(Banco banco, String status);

	@Query(value = "SELECT sum(c.valor) FROM public.fatura_venda_centro_custo_faturas c, public.fatura_contas_pagar  "
			+ "where f.id=c.id and f.status=0 and f.id=3", nativeQuery = true)
	double totalAbertoByStatus(int status, long id);
	
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contas_pagar  "
			+ "where date_part('month',data_vencimento)=?1 and date_part('year',data_vencimento)=?2 "
			+ " and status=?3", nativeQuery = true)
	double totalMesPeriodo(int mes,int ano,int status);
	
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contas_pagar  "
			+ "where date_part('year',data_vencimento)=?1  and status=?2", nativeQuery = true)
	double totalPeriodo(  int ano, int status);
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contas_pagar  "
			+ "where date_part('year',data_vencimento)<?1  and status=?2", nativeQuery = true)
	double totalPeriodoAnterio(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contas_pagar  "
			+ "where date_part('year',data_vencimento)>?1  and status=?2", nativeQuery = true)
	double totalPeriodoPosterior(int ano, int status);


}