package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.BancoRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.service.extrafatura.DatPeriodo;

@Service
public class FaturaContratoService extends BaseServic<FaturaContrato> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private BancoService bancoService;

	@Autowired
	private MovBancoService movBancoService;

	@Autowired
	private MovCentroCustoService movCentroCustoService;

	@Autowired
	private CentroCustoService centroCustoService;

	@Autowired
	private FaturaContratoRepository repo;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private BancoRepository bancoRepository;

	 
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
		FaturaContrato x = super.fingbyid(id);
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

		List<FaturaContrato> list = repo.findAllByContratoAndStatus(contrato, StatusActiv.ABERTO.getDescricao());
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
		list = repo.findAllByContratoAndStatus(contrato, StatusActiv.ABERTO.getDescricao());

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
				listParcelaAfazer(id, contrato.getPeriodo()), contrato.getDiaVencimento(), contrato.getDiaLeitura());
		List<FaturaContrato> faturas = new ArrayList<FaturaContrato>();

		// gerar faturanew array
		for (DatPeriodo c : listaDates) {
			FaturaContrato fc = new FaturaContrato(c, contrato);

			// gerar ficha leitura
			for (ItensContratoPatrimonio it : contrato.getItenspatrimonio()) {
				fc.getFichaLeitura().add(new FichaLeitura(it, contrato));
			}
			faturas.add(fc);
		}
		repo.saveAll(faturas);

	}

	public void quitar(FaturasDto obj) {

		Long i = obj.getBanco().getId();
		Banco banco = bancoRepository.findById(i).get();
		FaturaContrato fatura = fingbyid(obj.getId());
		BeanUtils.copyProperties(obj, fatura, getNullPropertyNames(obj)); // Perform update operation

		fatura.getBanco().setId(i);
		saveobj(fatura.getId(), fatura);

		for (CentroCustoFatura centroCustoFatura : fatura.getCentroCustoFaturas()) {
			centroCustoFatura.getCentroCusto()
					.setSaldo(centroCustoFatura.getCentroCusto().getSaldo() + centroCustoFatura.getValorFinal());
			centroCustoService.saveobj(centroCustoFatura.getCentroCusto().getId(), centroCustoFatura.getCentroCusto());
			MovCentroCusto c = new MovCentroCusto();
			c.setCentroCusto(centroCustoFatura.getCentroCusto());
			c.setDataMovimento(new Date());
			c.setValor(centroCustoFatura.getValorFinal());
			c.setDescricao("Recebimento de Fatura de contrato nro.:" + "" + fatura.getNumeroparcela() + " id:" + ""
					+ fatura.getId());
			c = movCentroCustoService.newobj(c);

		}

		// banco = bancoService.fingbyid(fatura.getBanco().getId());
		banco.setSaldo(banco.getSaldo() + fatura.getTotal());
		bancoService.saveobj(banco.getId(), banco);

		MovBanco m = new MovBanco();
		m.setBanco(banco);
		m.setDataMovimento(new Date());
		m.setValor(fatura.getTotal());
		m.setDescricao("Recebimento de Fatura de contrato nro.:" + "" + fatura.getNumeroparcela() + " id:" + ""
				+ fatura.getId());
		m.setFatura(fatura.getNome() + "-Fatura Contas Pagar de parcela:" + "" + fatura.getNumeroparcela() + " id:" + ""
				+ fatura.getId());
		m = movBancoService.newobj(m);

		fatura.setStatus(StatusActiv.QUIT.getDescricao());

		fatura = repo.save(fatura);
		/*
		 * contasPagar =
		 * contasPagarRepository.findById(f.getContasPagar().getId()).get(); if
		 * (contasPagar.getFaturasAberta().size() == 0) {
		 * contasPagar.setStatus(StatusActiv.QUIT.getDescricao());
		 * contasPagarRepository.save(contasPagar);
		 * 
		 * }
		 */
	}
}
