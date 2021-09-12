package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.AgregadoGrupoContrato;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.MovBanco;
import br.com.apidigitalweb.domin.financeiro.MovCentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.dto.financeiro.FaturasPagarDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.BancoRepository;
import br.com.apidigitalweb.repository.ContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;
import br.com.apidigitalweb.service.extrafatura.DatPeriodo;

@Service
public class FaturaContasPagarService extends BaseServic<FaturaContasPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContasPagarRepository repo;

	@Autowired
	private ContasPagarRepository contasPagarRepository;

	@Autowired
	private ContasaPagarService contasPagarService;

	@Autowired
	private BancoService bancoService;

	@Autowired
	private MovBancoService movBancoService;

	@Autowired
	private MovCentroCustoService movCentroCustoService;
	@Autowired
	private CentroCustoService centroCustoService;

	@Override
	public Page<FaturaContasPagar> findallpage(String find, Pageable page) {
		Page<FaturaContasPagar> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			try {
				x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		return findall;
	}

	/*
	 * @Override public FaturaContasPagar fingbyid(Long id) { FaturaContasPagar x =
	 * fingbyid(id); try {
	 * x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(),
	 * x.getTotal()));
	 * 
	 * } catch (Exception e) { // TODO: handle exception }
	 * 
	 * return x; }
	 */
	public FaturasPagarDto findbyiddto(Long id) {
		return new FaturasPagarDto(fingbyid(id));
	}

	public List<FaturaContasPagar> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaContasPagar> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}

	public List<FaturaContasPagar> getAllBanco(Banco banco, String status) {
		List<FaturaContasPagar> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}

	public List<FaturaContasPagar> getAllHistoricoContaPagar(HistoricoContaPagar historicoContaPagar, String status) {
		List<FaturaContasPagar> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByHistoricoAndStatus(historicoContaPagar, status);
		return getAll(getAllCentroCusto);
	}

	public List<FaturaContasPagar> getAll(List<FaturaContasPagar> lista) {
		List<FaturaContasPagar> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			try {
				x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(HistoricoContaPagar gr, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();

		// for (AgregadoGrupoContrato financeiro : gr.getAgregados())
		{
			CentroCustoFatura custoFatura = new CentroCustoFatura(gr.getCentroCusto(), valor);
			getAgregado.add(custoFatura);
		}
		return getAgregado;
	}

	/** gerar faturas **/
	// excluir faturas aberta
	public void excluirfaturasAberto(ContasPagar contasPagar) {
		List<FaturaContasPagar> listaberto = new ArrayList<FaturaContasPagar>();
		List<FaturaContasPagar> list = contasPagar.getFaturas();
		for (FaturaContasPagar f : list) {
			if (f.getStatus().equals(StatusActiv.ABERTO.getDescricao())) {
				listaberto.add(f);
			}
		}
		repo.deleteAll(listaberto);

	}

	public List<Integer> listParcelaConcluidos(ContasPagar contasPagar) {
		List<Integer> listaberto = new ArrayList<>();
		List<FaturaContasPagar> list = contasPagar.getFaturas();
		for (FaturaContasPagar f : list) {
			if (f.getStatus().equals(StatusActiv.QUIT.getDescricao())) {
				listaberto.add(f.getNumeroparcela());
			}
		}
		return listaberto;
	}

	public List<Integer> listParcelaAfazer(ContasPagar id, Integer j) {
		List<Integer> listParcelaAfazer = new ArrayList<>();

		for (int i = 1; i <= j; i++) {
			listParcelaAfazer.add(i);
		}
		listParcelaAfazer.removeAll(listParcelaConcluidos(id));
		return listParcelaAfazer;
	}

	public void regerarparcelas(Long id) {
		ContasPagar contasPagar = contasPagarService.fingbyid(id);
		// deletar faturas
		excluirfaturasAberto(contasPagar);

		// lista de data das faturas a fazer
		List<DatPeriodo> listaDates = DatPeriodo.listaDates(contasPagar.getFinanceiro().getDatavencimento(),
				listParcelaAfazer(contasPagar, contasPagar.getFinanceiro().getParcelas()));

		List<FaturaContasPagar> faturas = new ArrayList<FaturaContasPagar>();

		// gerar faturanew array
		for (DatPeriodo c : listaDates) {
			FaturaContasPagar fc = new FaturaContasPagar(c, contasPagar);
			fc.setValor(contasPagar.getValor() / listaDates.size());

			faturas.add(fc);
		}
		repo.saveAll(faturas);

	}

	public List<FaturasDto> faturasopen() {

		List<FaturasDto> faturaopen = repo.findByStatusDTO(StatusActiv.ABERTO.getDescricao());

		return faturaopen;
	}

	/**** Quitar Fatura ****/
	public void quitarfatura(Long id) {

		FaturaContasPagar f = fingbyid(id);
		
		Banco banco = bancoService.fingbyid(f.getBanco().getId());
		banco.setSaldo(banco.getSaldo() - f.getTotal());
		bancoService.saveobj(banco.getId(), banco);
		
		CentroCusto centroCusto = centroCustoService.fingbyid(f.getCentroCusto().getId());
		centroCusto.setSaldo(centroCusto.getSaldo() - f.getTotal());
		centroCustoService.saveobj(centroCusto.getId(), centroCusto);

		
		
		MovBanco m = new MovBanco();
		m.setBanco(banco);
		m.setDataMovimento(new Date());
		m.setValor(f.getTotal());
		m.setDescricao(
				"pagamento de Fatura Contas Pagar Fatura nro.:" + "" + f.getNumeroparcela() + " id:" + "" + f.getId());
		m.setFatura(
				f.getNome() + "-Fatura Contas Pagar de parcela:" + "" + f.getNumeroparcela() + " id:" + "" + f.getId());
		m = movBancoService.newobj(m);

		MovCentroCusto c = new MovCentroCusto();
		c.setCentroCusto(f.getCentroCusto());
		c.setDataMovimento(new Date());
		c.setValor(f.getTotal());
		c.setDescricao("pagamento de Fatura Contas Pagar nro.:" + "" + f.getNumeroparcela() + " id:" + "" + f.getId());
		c = movCentroCustoService.newobj(c);

		f.setStatus(StatusActiv.QUIT.getDescricao());

		f = repo.save(f);
		ContasPagar contasPagar = contasPagarRepository.findById(f.getContasPagar().getId()).get();
		if (contasPagar.getFaturasAberta().size() == 0) {
			contasPagar.setStatus(StatusActiv.QUIT.getDescricao());
			contasPagarRepository.save(contasPagar);

		}

	}
}