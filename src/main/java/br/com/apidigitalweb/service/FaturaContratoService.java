package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
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
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.financeiro.AgregadoGrupoFinanceiro;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.ItensOrdemVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import br.com.apidigitalweb.service.extrafatura.DatPeriodo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class FaturaContratoService extends BaseServic<FaturaContrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContratoRepository repo;

	@Autowired
	private ContratoService contratoService;

	@Override
	public Page<FaturaContrato> findallpage(String find, Pageable page) {
		Page<FaturaContrato> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			try {
				x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		return findall;
	}

	@Override
	public FaturaContrato fingbyid(Long id) {
		FaturaContrato x = fingbyid(id);
		try {
			x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

		} catch (Exception e) {
			// TODO: handle exception
		}

		return super.fingbyid(id);
	}

	public List<FaturaContrato> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaContrato> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}

	public List<FaturaContrato> getAllBanco(Banco banco, String status) {
		List<FaturaContrato> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}

	public List<FaturaContrato> getAll(List<FaturaContrato> lista) {
		List<FaturaContrato> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			try {
				x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(GrupoFinanceiroContrato gr, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();

		for (AgregadoGrupoContrato financeiro : gr.getAgregados()) {
			CentroCustoFatura custoFatura = new CentroCustoFatura(financeiro.getCentroCusto(), valor);
			getAgregado.add(custoFatura);
		}
		return getAgregado;
	}

	/** gerar faturas **/
	// excluir faturas aberta
	public void excluirfaturasAberto(Contrato contrato) {
		
		List<FaturaContrato> list=repo.findAllByContratoAndStatus(contrato, StatusActiv.ABERTO.getDescricao());
		for (FaturaContrato faturaContrato : list) {
			faturaContrato.setFichaLeitura(new ArrayList<>());
			  
		}
		
		 repo.saveAll(list);
		 for (FaturaContrato faturaContrato : list) {
				faturaContrato.setFichaLeitura(new ArrayList<>());
				// repo.deleteById(faturaContrato.getId());
				try {
					repo.deleteAllByContratoIdAndStatus(faturaContrato.getId(), contrato.getId(),
							StatusActiv.ABERTO.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		 list=repo.findAllByContratoAndStatus(contrato, StatusActiv.ABERTO.getDescricao());
		
	}

	public List<Integer> listParcelaConcluidos(Long id) {
		return repo.listaNumeroparcelaContratoIdAndStatus(id, StatusActiv.QUIT.getDescricao());
	}

	public List<Integer> listParcelaAfazer(Long id, Integer j) {
		List<Integer> listParcelaAfazer = new ArrayList<>();
		for (int i = 1; i <= j; i++) {
			listParcelaAfazer.add(i);
		}
		listParcelaAfazer.removeAll(listParcelaConcluidos(id));
		return listParcelaAfazer;
	}

	public void regerarparcelas(Long id) {
		Contrato contrato = contratoService.fingbyid(id);
		// deletar faturas
		excluirfaturasAberto(contrato);
		
		// lista de data das faturas a fazer
		List<DatPeriodo> listaDates = DatPeriodo.listaDates(contrato.getDataInicio(),
				listParcelaAfazer(id, contrato.getPeriodo()),contrato.getDiaVencimento(), contrato.getDiaLeitura());
		List<FaturaContrato> faturas = new ArrayList<FaturaContrato>();
		
		// gerar faturanew array
		for (DatPeriodo c : listaDates) {
			FaturaContrato fc = new FaturaContrato(c, contrato);
			
			//gerar ficha leitura
			for (ItensContratoPatrimonio it : contrato.getItenspatrimonio()) {
				fc.getFichaLeitura().add(new FichaLeitura(it, contrato));
			}
			faturas.add(fc);
		}
		repo.saveAll(faturas);

	}

	
}
