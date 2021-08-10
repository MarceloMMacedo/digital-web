package br.com.apidigitalweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;
import br.com.apidigitalweb.dto.financeiro.contasreceber.ResumoContasReceber;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.repository.BancoRepository;
import br.com.apidigitalweb.repository.CentroCustoRepository;
import br.com.apidigitalweb.repository.ClienteRepository;
import br.com.apidigitalweb.repository.ContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import br.com.apidigitalweb.service.BancoService;
import br.com.apidigitalweb.service.ContasaPagarService;
import br.com.apidigitalweb.service.FaturaContasPagarService;
import br.com.apidigitalweb.service.componente.ReportFinanceiroService;

@Controller
public class ControlleTest implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	FaturaVendasRepository fatuaraVendasRepository;
	@Autowired
	ClienteRepository crepo;
	@Autowired
	CentroCustoRepository centroCustoRepository;

	@Autowired
	BancoRepository bancoRepository;
	
	@Autowired
	BancoService bancoService;

	@Autowired
	ContasaPagarService contasPagarRepository;

	@Autowired
	FaturaContratoRepository  faturaContratoRepository;
	
	@Autowired
	ReportFinanceiroService reportFinanceiroService;

	@GetMapping(value = "/teste1")
	public ResponseEntity<ResumoContasReceber> test1(){
		
		
	return	ResponseEntity.ok(reportFinanceiroService.contasReceber(2021));//(ItemMonthReportDto.itemMonthReportDto(faturaContratoRepository.itemMonthReportDtos(2030,StatusActiv.ABERTO.getId())));
		
	}
	
	
	@GetMapping(value = "/teste")
	public ResponseEntity<List<Banco>> test(
			@RequestParam(defaultValue = "", value = "nome") String nome, Pageable page) {
		FaturaVenda ast = new FaturaVenda();
		
		Banco banco=new Banco();
		banco.setBanco("banco1");
		banco=bancoRepository.save(banco);
		
		CentroCusto ce = centroCustoRepository.findById((long) 1).get();
		CentroCusto ce1 = centroCustoRepository.findById((long) 2).get();
		CentroCusto ce2 = centroCustoRepository.findById((long) 3).get();
		List<CentroCustoFatura> centroCustoFaturas = new ArrayList<CentroCustoFatura>();
		centroCustoFaturas.add(new CentroCustoFatura(ce, 20.0));
		centroCustoFaturas.add(new CentroCustoFatura(ce1, 23.6));
		centroCustoFaturas.add(new CentroCustoFatura(ce2, 29.36));

		Cliente c = crepo.findById((long) 1).get();

		ast.setStatus(StatusActiv.ABERTO.getDescricao());		
		ast.setCliente(c);
		ast.setValor(251);
		ast.setCentroCustoFaturas(centroCustoFaturas);
		ast.setBanco(banco);		
		fatuaraVendasRepository.save(ast);
		
		ast = new FaturaVenda();
		ast.setStatus(StatusActiv.ABERTO.getDescricao());
		ast.setCliente(c);
		ast.setValor(23);
		ast.setCentroCustoFaturas(centroCustoFaturas);
		ast.setBanco(banco);		
		fatuaraVendasRepository.save(ast);
		
		ast = new FaturaVenda();
		ast.setStatus(StatusActiv.ABERTO.getDescricao());
		ast.setCliente(c);
		ast.setValor(252);
		ast.setCentroCustoFaturas(centroCustoFaturas);
		ast.setBanco(banco);		
		fatuaraVendasRepository.save(ast);
		
		ast = new FaturaVenda();
		ast.setStatus(StatusActiv.ABERTO.getDescricao());
		ast.setCliente(c);
		ast.setValor(25);
		ast.setBanco(banco);		
		fatuaraVendasRepository.save(ast);
		
		ast = new FaturaVenda();
		ast.setStatus(StatusActiv.ABERTO.getDescricao());
		ast.setCliente(c);
		ast.setValor(251);
		ast.setCentroCustoFaturas(centroCustoFaturas);
		ast.setBanco(banco);
		fatuaraVendasRepository.save(ast);
		
		System.out.println(fatuaraVendasRepository.findAll());

		//System.out.println(fatuaraVendasRepository.totalAbertoByStatus(StatusActiv.ABERTO.getId(), ast.getId()));
		
		 
 
		return ResponseEntity.ok(bancoService.getAll());
		
		
	}

}
