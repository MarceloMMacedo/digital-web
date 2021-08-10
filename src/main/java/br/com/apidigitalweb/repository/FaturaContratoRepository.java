package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;

@Repository
public interface FaturaContratoRepository extends JpaRepository<FaturaContrato, Long> {

	List<FaturaContrato> findAllByCentroCustoAndStatus(CentroCusto centroCusto, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.centroCusto=?1 and e.status=?2")
	double totalAbertoByCentroCusto(CentroCusto centroCusto, String status);

	List<FaturaContrato> findAllByClienteAndStatus(Cliente cliente, String status);

	List<FaturaContrato> findAllByBancoAndStatus(Banco cliente, String status);

	List<FaturaContrato> findByStatus(String status);

	List<FaturaContrato> findAllByContratoAndStatus(Contrato contrato, String status);

	List<FaturaContrato> findAllByContrato(Contrato contrato);

	List<FaturaContrato> findAllByGrupoFinanceiroAndStatus(GrupoFinanceiroContrato grupoFinanceiro, String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaContrato f where f.banco=?1 and f.status=?2")
	List<FaturasDto> allBanco(Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.cliente=?1 and e.status=?2")
	double totalAbertoByCliente(Cliente cliente, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.contrato=?1 and e.status=?2")
	double totalAbertoByContrato(Contrato contrato, String status);
	
	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.status=?1")
	double totalAll( String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.grupoFinanceiro=?1 and e.status=?2")
	double totalAbertoByGrupoFinanceiro(GrupoFinanceiroContrato grupoFinanceiro, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaContrato e where e.banco=?1 and e.status=?2")
	double totalAbertoByBanco(Banco banco, String status);

	@Query(value = "SELECT sum(c.valor) FROM public.fatura_venda_centro_custo_faturas c, public.fatura_contrato f\n"
			+ "where f.id=c.id and f.status=0 and f.id=3", nativeQuery = true)
	double totalAbertoByStatus(int status, long id);

	/** Gerar Faturas **/
	@Query(value = " DELETE FROM fatura_contrato  	WHERE   id=?1", nativeQuery = true)
	void deleteAllByContratoIdAndStatus(Long id, long contratoid, Integer status);

	@Query("SELECT e.numeroparcela from FaturaContrato e where e.contrato.id=?1 and e.status=?2")
	List<Integer> listaNumeroparcelaContratoIdAndStatus(Long id, String status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contrato  "
			+ "where date_part('month',data_vencimento)=?1 and date_part('year',data_vencimento)=?2 "
			+ " and status=?3", nativeQuery = true)
	double totalMesPeriodo(int mes, int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contrato  "
			+ "where date_part('year',data_vencimento)=?1  and status=?2", nativeQuery = true)
	double totalPeriodo(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contrato  "
			+ "where date_part('year',data_vencimento)<?1  and status=?2", nativeQuery = true)
	double totalPeriodoAnterio(int ano, int status);
//	2021-09-06 
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_contrato  "
			+ "where date_part('year',data_vencimento)>?1  and status=?2", nativeQuery = true)
	double totalPeriodoPosterior(int ano, int status);
	
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total,\n"
			+ "date_part('month',data_vencimento) as month\n"
			+ "from fatura_contrato   where date_part('year',data_vencimento)=?1 and   status=?2 \n"
			+ "group by date_part('month',data_vencimento)\n"
			+ "order by date_part('month',data_vencimento)", nativeQuery = true)
	List<Object[]> itemMonthReportDtos(int ano, int status);
	
}