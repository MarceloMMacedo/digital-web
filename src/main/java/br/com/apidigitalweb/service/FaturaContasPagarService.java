package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.enuns.StatusActiv;
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

	@Override
	public FaturaContasPagar fingbyid(Long id) {
		FaturaContasPagar x = fingbyid(id);
		try {
			x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

		} catch (Exception e) {
			// TODO: handle exception
		}

		return x;
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

}