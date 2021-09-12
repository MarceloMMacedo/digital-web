/**
 * 
 */
package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.repository.BancoRepository;
import br.com.apidigitalweb.repository.FaturaOrdemServicoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;

/**
 * @author macedo
 *
 */
@Service
public class BancoService extends BaseServic<Banco> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BancoRepository repo;
	@Autowired
	private FaturaContasPagarRepository faturaContasPagarRepository;

	@Autowired
	private FaturaContratoRepository faturaContratoRepository;

	@Autowired
	private FaturaOrdemServicoRepository faturaOrdemRepository;

	@Autowired
	private FaturaVendasRepository faturaVendaRepository;

	@Autowired
	private FaturaContasPagarService faturaContasPagarService;

	@Autowired
	private FaturaContratoService faturaContratoService;

	@Autowired
	private FaturaOrdemServicoService faturaOrdemServico;

	@Autowired
	private FaturaVendaService faturaVendaService;

	@Override
	public Banco fingbyid(Long id) {
		Banco x = super.fingbyid(id);
		{
			double valor = 0;
			try {
				List<FaturaContrato> contratos = faturaContratoRepository
						.findByStatus(StatusActiv.ABERTO.getDescricao());

				for (FaturaContrato faturaContrato : contratos) {
					valor += faturaContrato.getTotal();
				}
				// valor += faturaContratoRepository.totalAbertoByBanco(x,
				// StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaVendaRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaOrdemRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			double valorsaida = 0;
			try {
				valorsaida += faturaContasPagarRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}

			x.getFaturasAbertaEntrada().addAll(faturaContratoRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasAbertaEntrada().addAll(faturaOrdemRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasAbertaEntrada().addAll(faturaVendaRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasaAbertaPagar()
					.addAll(faturaContasPagarRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));

			x.setBanco(valor, valorsaida);

		}
		return x;
	}

	@Override
	public List<Banco> getAll() {
		List<Banco> getAll = super.getAll();
		getAll.stream().forEach(x -> {
			double valor = 0;
			try {
				valor += faturaContratoRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaVendaRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaOrdemRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			double valorsaida = 0;
			try {
				valorsaida += faturaContasPagarRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			x.getFaturasAbertaEntrada().addAll(faturaContratoRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasAbertaEntrada().addAll(faturaOrdemRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasAbertaEntrada().addAll(faturaVendaRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			x.getFaturasaAbertaPagar()
					.addAll(faturaContasPagarRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));

			x.setBanco(valor, valorsaida);

		});
		return getAll;
	}

	@SuppressWarnings("unused")
	@Override
	public Page<Banco> findallpage(String find, Pageable page) {

		Page<Banco> findallpage = repo.findByBancoContainingIgnoreCaseOrBancoIsNull(find, page);

		findallpage.getContent().forEach(x -> {
			double valor = 0;
			try {
				valor += faturaContratoRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaVendaRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				valor += faturaOrdemRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}
			double valorsaida = 0;
			try {
				valorsaida += faturaContasPagarRepository.totalAbertoByBanco(x, StatusActiv.ABERTO.getDescricao());
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				x.getFaturasAbertaEntrada()
						.addAll(faturaContratoRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				x.getFaturasAbertaEntrada()
						.addAll(faturaOrdemRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				x.getFaturasAbertaEntrada()
						.addAll(faturaVendaRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				x.getFaturasaAbertaPagar()
						.addAll(faturaContasPagarRepository.allBanco(x, StatusActiv.ABERTO.getDescricao()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			x.setBanco(valor, valorsaida);

		});

		return findallpage;
	}

	/*
	 * private List<FaturasDto> faturasDtosVendas(List<FaturaVendaWeb> faturaVendas,
	 * String origem) { List<FaturasDto> faturasDto = new ArrayList<>(); for
	 * (BaseFatura b : faturaVendas) { faturasDto.add(new FaturasDto(b, origem)); }
	 * 
	 * return faturasDto; }
	 * 
	 * private List<FaturasDto> faturaOrdemServicos(List<FaturaOrdemServico>
	 * faturas, String origem) { List<FaturasDto> faturasDtos = new ArrayList<>();
	 * for (BaseFatura b : faturas) { FaturasDto f = new FaturasDto(b, origem);
	 * faturasDtos.add(f); }
	 * 
	 * return faturasDtos; }
	 * 
	 * private List<FaturasDto> faturaContratos(List<FaturaContrato> faturas, String
	 * origem) { List<FaturasDto> faturasDtos = new ArrayList<>(); for (BaseFatura b
	 * : faturas) { FaturasDto f = new FaturasDto(b, origem); faturasDtos.add(f); }
	 * 
	 * return faturasDtos; }
	 * 
	 * private List<FaturasDto> faturaCP(List<FaturaContasPagar> faturas, String
	 * origem) { List<FaturasDto> faturasDtos = new ArrayList<>(); for (BaseFatura b
	 * : faturas) { FaturasDto f = new FaturasDto(b, origem); faturasDtos.add(f); }
	 * 
	 * return faturasDtos; }
	 */
}
