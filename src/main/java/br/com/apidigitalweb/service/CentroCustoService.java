/**
 * 
 */
package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.repository.CentroCustoRepository;

/**
 * @author macedo
 *
 */
@Service
public class CentroCustoService extends BaseServic<CentroCusto> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CentroCustoRepository repo;
	@Autowired
	private FaturaContasPagarService faturaContasPagarService;

	@Autowired
	private FaturaContratoService faturaContratoService;

	@Autowired
	private FaturaOrdemServicoService faturaOrdemServico;

	@Autowired
	private FaturaVendaService faturaVendaService;

	@Override
	public CentroCusto fingbyid(Long id) {
		CentroCusto x = super.fingbyid(id);
		{
			double valor = 0;

			// Faturas Venda
			List<FaturaVendaLoja> faturaVendas = faturaVendaService.getAllCentroCusto(x, StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturasDtosVendas(faturaVendas, TipoFaturaEnum.Venda.getDescricao()));
			for (FaturaVendaLoja faturaVenda : faturaVendas) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}

			// Faturas Servicos
			List<FaturaOrdemServico> faturaOrdemServicos = faturaOrdemServico.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaOrdemServicos(faturaOrdemServicos, TipoFaturaEnum.Servico.getDescricao()));
			for (FaturaOrdemServico faturaVenda : faturaOrdemServicos) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas Contrato
			List<FaturaContrato> listfaturaContrato = faturaContratoService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaContratos(listfaturaContrato, TipoFaturaEnum.Contrato.getDescricao()));
			for (FaturaContrato faturaVenda : listfaturaContrato) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas pagar
			double valors = 0;
			List<FaturaContasPagar> listfaturaContasPagar = faturaContasPagarService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturaCP(listfaturaContasPagar, TipoFaturaEnum.ContasPagar.getDescricao()));
			for (FaturaContasPagar faturaVenda : listfaturaContasPagar) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valors += centroCustoFatura.getValorFinal();
				}
			}
			x.setEntradasFuturo(valor);
			x.setSaidasFuturo(valors);
		}
		return x;
	}

	@Override
	public List<CentroCusto> getAll() {
		List<CentroCusto> getAll = super.getAll();
		getAll.stream().forEach(x -> {
			double valor = 0;

			// Faturas Venda
			List<FaturaVendaLoja> faturaVendas = faturaVendaService.getAllCentroCusto(x, StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturasDtosVendas(faturaVendas, TipoFaturaEnum.Venda.getDescricao()));
			for (FaturaVendaLoja faturaVenda : faturaVendas) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}

			// Faturas Servicos
			List<FaturaOrdemServico> faturaOrdemServicos = faturaOrdemServico.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaOrdemServicos(faturaOrdemServicos, TipoFaturaEnum.Servico.getDescricao()));
			for (FaturaOrdemServico faturaVenda : faturaOrdemServicos) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas Contrato
			List<FaturaContrato> listfaturaContrato = faturaContratoService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaContratos(listfaturaContrato, TipoFaturaEnum.Contrato.getDescricao()));
			for (FaturaContrato faturaVenda : listfaturaContrato) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas pagar
			double valors = 0;
			List<FaturaContasPagar> listfaturaContasPagar = faturaContasPagarService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturaCP(listfaturaContasPagar, TipoFaturaEnum.ContasPagar.getDescricao()));
			for (FaturaContasPagar faturaVenda : listfaturaContasPagar) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valors += centroCustoFatura.getValorFinal();
				}
			}
			x.setEntradasFuturo(valor);
			x.setSaidasFuturo(valors);
		});

		return getAll;
	}

	/*@SuppressWarnings("unused")
	@Override
	public Page<CentroCusto> findallpage(String find, Pageable page) {

		Page<CentroCusto> findallpage = repo.findByNomeContainingIgnoreCaseOrNomeIsNull(find, page);

		findallpage.getContent().forEach(x -> {
			double valor = 0;

			// Faturas Venda
			List<FaturaVendaWeb> faturaVendas = faturaVendaService.getAllCentroCusto(x, StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturasDtosVendas(faturaVendas, TipoFaturaEnum.Venda.getDescricao()));
			for (FaturaVendaWeb faturaVenda : faturaVendas) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}

			// Faturas Servicos
			List<FaturaOrdemServico> faturaOrdemServicos = faturaOrdemServico.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaOrdemServicos(faturaOrdemServicos, TipoFaturaEnum.Servico.getDescricao()));
			for (FaturaOrdemServico faturaVenda : faturaOrdemServicos) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas Contrato
			List<FaturaContrato> listfaturaContrato = faturaContratoService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada()
					.addAll(faturaContratos(listfaturaContrato, TipoFaturaEnum.Contrato.getDescricao()));
			for (FaturaContrato faturaVenda : listfaturaContrato) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valor += centroCustoFatura.getValorFinal();
				}
			}
			// Faturas pagar
			double valors = 0;
			List<FaturaContasPagar> listfaturaContasPagar = faturaContasPagarService.getAllCentroCusto(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAbertaEntrada().addAll(faturaCP(listfaturaContasPagar, TipoFaturaEnum.Contrato.getDescricao()));
			for (FaturaContasPagar faturaVenda : listfaturaContasPagar) {
				for (CentroCustoFatura centroCustoFatura : faturaVenda.getCentroCustoFaturas()) {
					valors += centroCustoFatura.getValorFinal();
				}
			}
			x.setEntradasFuturo(valor);
			x.setSaidasFuturo(valors);
		});

		return findallpage;
	}
*/
	private List<FaturasDto> faturasDtosVendas(List<FaturaVendaLoja> faturaVendas, String origem) {
		List<FaturasDto> faturasDto = new ArrayList<>();
		for (BaseFatura b : faturaVendas) {
			faturasDto.add(new FaturasDto(b, origem));
		}

		return faturasDto;
	}

	private List<FaturasDto> faturaOrdemServicos(List<FaturaOrdemServico> faturas, String origem) {
		List<FaturasDto> faturasDtos = new ArrayList<>();
		for (BaseFatura b : faturas) {
			FaturasDto f = new FaturasDto(b, origem);
			faturasDtos.add(f);
		}

		return faturasDtos;
	}

	private List<FaturasDto> faturaContratos(List<FaturaContrato> faturas, String origem) {
		List<FaturasDto> faturasDtos = new ArrayList<>();
		for (BaseFatura b : faturas) {
			FaturasDto f = new FaturasDto(b, origem);
			faturasDtos.add(f);
		}

		return faturasDtos;
	}

	private List<FaturasDto> faturaCP(List<FaturaContasPagar> faturas, String origem) {
		List<FaturasDto> faturasDtos = new ArrayList<>();
		for (BaseFatura b : faturas) {
			FaturasDto f = new FaturasDto(b, origem);
			faturasDtos.add(f);
		}

		return faturasDtos;
	}
}
