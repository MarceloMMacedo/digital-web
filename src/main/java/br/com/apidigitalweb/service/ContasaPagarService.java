package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDto;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDtoReport;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.dto.financeiro.HistoricoContasPagarDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.ContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;

@Service
public class ContasaPagarService extends BaseServic<ContasPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContasPagarRepository repo;

	@Autowired
	private FaturaContasPagarRepository contasPagarRepository;

	@Autowired
	private FaturaContasPagarService contasPagarService;

	@Autowired
	private FaturaContratoRepository contratoRepository;

	public List<ContasPagarDtoReport> contaspagardtosrepor() {

		List<ContasPagarDtoReport> lista = new ArrayList<ContasPagarDtoReport>();

		List<GrupoContaPagar> grupoContaPagars = repo.findAllByGrupoContaPagar(StatusActiv.ABERTO.getDescricao());

		for (GrupoContaPagar g : grupoContaPagars) {
			double valor = 0;
			ContasPagarDtoReport dtoReport = new ContasPagarDtoReport();
			dtoReport.setNome(g.getNome());

			for (HistoricoContaPagar h : g.getHistoricos()) {

				HistoricoContasPagarDto hs = new HistoricoContasPagarDto();
				hs.setNome(h.getNome());

				List<FaturaContasPagar> cps = contasPagarRepository.findAllByHistoricoAndStatus(h,
						StatusActiv.ABERTO.getDescricao());

				List<FaturaContasPagar> sortedList = cps.stream()
						.sorted(Comparator.comparing(FaturaContasPagar::getDataVencimento))
						.collect(Collectors.toList());

				for (FaturaContasPagar b : sortedList) {
					hs.getAnaliticoDtos().add(new FaturasDto(b));
				}
				if (cps.size() > 0)
					dtoReport.getHistoricoContasPagarDtos().add(hs);
				if (cps.size() > 0)
					lista.add(dtoReport);
			}

		}

		/*
		 * List<ContasPagar> listap =
		 * repo.findAllContasPagarPagar(StatusActiv.ABERTO.getDescricao()); for
		 * (ContasPagar contasPagar : listap) { if (contasPagar.getFaturas().size() > 0)
		 * lista.add(new ContasPagarDtoReport(contasPagar)); }
		 */
		return lista;

	}

	
	@Override
	public ContasPagar newobj(ContasPagar obj) {
		// TODO Auto-generated method stub
		return super.newobj(obj);
	}

	@Override
	public void posNewObj(ContasPagar obj) {
		contasPagarService.regerarparcelas(obj.getId());
	}

	public List<ContasPagarDto> contasPagarDtos() {
		List<ContasPagarDto> contasPagarDtos = new ArrayList<ContasPagarDto>();
		List<ContasPagar> all = getAll();

		for (ContasPagar c : getAll()) {
			contasPagarDtos.add(new ContasPagarDto(c));
		}
		return contasPagarDtos;
	}

	public ContasPagarDto getcontaspagardto(Long id) {
		return new ContasPagarDto(fingbyid(id));
	}

	@Override
	public void prenew(ContasPagar obj) {
		obj.setNome(obj.getHistorico().getNome() + "/" + "" + obj.getFinanceiro().getParcelas());
	}

}
