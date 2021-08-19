package br.com.apidigitalweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;

@Repository
public interface FaturaOrdemServicoRepository extends JpaRepository<FaturaOrdemServico, Long> {

	List<FaturaOrdemServico> findAllByCentroCustoAndStatus(CentroCusto centroCusto, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaOrdemServico e where e.centroCusto=?1 and e.status=?2")
	double totalAbertoByCentroCusto(CentroCusto centroCusto, String status);

	List<FaturaOrdemServico> findByClienteAndStatus(Cliente cliente, String status);

	List<FaturaOrdemServico> findAllByBancoAndStatus(Banco cliente, String status);

	List<FaturaOrdemServico> findByStatus(String status);

	List<FaturaOrdemServico> findByOrdemServicoAndStatus(OrdemServico OrdemServico, String status);

	/*
	 * @Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaOrdemServico f "
	 * +
	 * "where f.cliente=?1 and f.status=?2 and  f.dataVencimento BETWEEN =?3 AND =?4  order by f.dataVencimento"
	 * ) List<FaturasDto> findAllClienteAndStatusBetoweend(Cliente cliente, String
	 * status, Date inicio, Date fim);
	 */
	List<FaturaOrdemServico> findAllByStatusAndDataVencimentoBetweenOrderByDataVencimento(String status, Date inicio,
			Date fim);

	List<FaturaOrdemServico> findAllByClienteAndStatusAndDataVencimentoBetweenOrderByDataVencimento(Cliente cliente, String status,
			Date inicio, Date fim);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaOrdemServico f where f.cliente=?1 and f.status=?2 order by f.dataVencimento")
	List<FaturasDto> findAllClienteAndStatus(Cliente cliente, String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaOrdemServico f where f.banco=?1 and f.status=?2")
	List<FaturasDto> allBanco(Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaOrdemServico e where e.cliente=?1 and e.status=?2")
	double totalAbertoByCliente(Cliente cliente, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaOrdemServico e where e.ordemServico=?1 and e.status=?2")
	double totalAbertoByOrdemServico(OrdemServico ordemServico, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaOrdemServico e where e.banco=?1 and e.status=?2")
	double totalAbertoByBanco(Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaOrdemServico e where e.status=?1")
	double totalAll(String status);

	@Query(value = "SELECT sum(c.valor) FROM public.fatura_venda_centro_custo_faturas c, public.fatura_ordem_servico f\n"
			+ "where f.id=c.id and f.status=0 and f.id=3", nativeQuery = true)
	double totalAbertoByStatus(int status, long id);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_ordem_servico  "
			+ "where date_part('month',data_vencimento)=?1 and date_part('year',data_vencimento)=?2 "
			+ " and status=?3", nativeQuery = true)
	double totalMesPeriodo(int mes, int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_ordem_servico  "
			+ "where date_part('year',data_vencimento)=?1  and status=?2", nativeQuery = true)
	double totalPeriodo(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_ordem_servico  "
			+ "where date_part('year',data_vencimento)<?1  and status=?2", nativeQuery = true)
	double totalPeriodoAnterio(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_ordem_servico  "
			+ "where date_part('year',data_vencimento)>?1  and status=?2", nativeQuery = true)
	double totalPeriodoPosterior(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total,\n"
			+ "date_part('month',data_vencimento) as month\n"
			+ "from fatura_ordem_servico   where date_part('year',data_vencimento)=?1 and   status=?2 \n"
			+ "group by date_part('month',data_vencimento)\n"
			+ "order by date_part('month',data_vencimento)", nativeQuery = true)
	List<Object[]> itemMonthReportDtos(int ano, int status);

}