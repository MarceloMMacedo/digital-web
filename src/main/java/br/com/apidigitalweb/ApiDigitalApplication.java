
package br.com.apidigitalweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.apidigitalweb.controller.estoque.ListaImagens;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Medidor;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.DescricaoAnuncio;
import br.com.apidigitalweb.domin.estoque.FornecedorProduto;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.domin.estoque.Modelo;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.AgregadoGrupoFinanceiro;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.repository.AnuncioLojaRepository;
import br.com.apidigitalweb.repository.BancoRepository;
import br.com.apidigitalweb.repository.CentroCustoRepository;
import br.com.apidigitalweb.repository.ClienteRepository;
import br.com.apidigitalweb.repository.ContratoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import br.com.apidigitalweb.repository.FornecedorRepository;
import br.com.apidigitalweb.repository.FuncionarioRepository;
import br.com.apidigitalweb.repository.GrupoFinanceiroAnuncioRepository;
import br.com.apidigitalweb.repository.ModeloRepository;
import br.com.apidigitalweb.repository.OrdemVendasRepository;
import br.com.apidigitalweb.repository.PatrimonioRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;
import br.com.apidigitalweb.service.BancoService;

@EnableFeignClients
@SpringBootApplication
public class ApiDigitalApplication implements CommandLineRunner {

	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	FornecedorRepository fornecedorRepository;
	@Autowired
	ModeloRepository modeloRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	CentroCustoRepository centroCustoRepository;
	@Autowired
	AnuncioLojaRepository anuncioLojaRepository;
	@Autowired
	GrupoFinanceiroAnuncioRepository financeiroProdutoLojaRepository;
	@Autowired
	ReceitaWsFeignPessoaJuridica feignFornecedor;
	@Autowired
	PatrimonioRepository patrimonioRepository;
	@Autowired
	ContratoRepository contratoRepository;

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	FaturaVendasRepository fatuaraVendasRepository;
	@Autowired
	ClienteRepository crepo; 

	@Autowired
	BancoRepository bancoRepository;
	
	@Autowired
	BancoService bancoService;

	@Autowired
	private OrdemVendasRepository ordemVendasRepository;
	@Override
	public void run(String... args) throws Exception {
		try {
		 	int i ;
		/*	CentroCusto cc = new CentroCusto(1.8, 2.6, 3.9, "Centro1");
			cc = centroCustoRepository.save(cc);
			cc = new CentroCusto(1.8, 2.6, 3.9, "Centro2");
			cc = centroCustoRepository.save(cc);
			cc = new CentroCusto(1.8, 2.6, 3.9, "Centro3");
			cc = centroCustoRepository.save(cc);

			int i = financeiroProdutoLojaRepository.findAll().size();
			GrupoFinanceiroAnuncio gl = null;
			if (financeiroProdutoLojaRepository.findAll().size() == 0
					|| financeiroProdutoLojaRepository.findAll() == null) {
				List<AgregadoGrupoFinanceiro> agregadoGrupoFinanceiros = new ArrayList<AgregadoGrupoFinanceiro>();

				agregadoGrupoFinanceiros.add(new AgregadoGrupoFinanceiro("agregado1", 05.0, cc));
				agregadoGrupoFinanceiros.add(new AgregadoGrupoFinanceiro("agregado2", 4.0, cc));
				agregadoGrupoFinanceiros.add(new AgregadoGrupoFinanceiro("agregado3", 05.50, cc));
				agregadoGrupoFinanceiros.add(new AgregadoGrupoFinanceiro("agregado4", 7.10, cc));

				gl = new GrupoFinanceiroAnuncio("grupo A", agregadoGrupoFinanceiros);

				 
				financeiroProdutoLojaRepository.save(gl);
			}
			*/
			i = funcionarioRepository.findAll().size();
			if (funcionarioRepository.findAll().size() == 0 || funcionarioRepository.findAll() == null) {
				Funcionario f = new Funcionario();

				f.setNome("M");
				f.setImagem("a2e07eef-2784-4c9a-a9a2-ed36f785f868");
				f.setExtension("png");
				java.util.List<String> list = new LinkedList<String>();
				list.add("Administrador Geral");
				f.setRolers("Administrador Geral");
				f.setEmail("marcelo_macedo01@hotmail.com");
				f.setStatus(StatusActiv.ATIVO.getDescricao());
				f.setSenha(encoder().encode("123456"));
				f = funcionarioRepository.save(f);
			 
			 
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ApiDigitalApplication.class, args);
	}

}
/*
 * package br.com.apidigitalweb;
 * 
 * import java.util.ArrayList; import java.util.Date; import
 * java.util.LinkedList; import java.util.List; import java.util.UUID;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.CommandLineRunner; import
 * org.springframework.boot.SpringApplication; import
 * org.springframework.boot.autoconfigure.SpringBootApplication; import
 * org.springframework.cloud.openfeign.EnableFeignClients; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * import br.com.apidigitalweb.controller.estoque.ListaImagens; import
 * br.com.apidigitalweb.domin.contratos.Contrato; import
 * br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio; import
 * br.com.apidigitalweb.domin.contratos.Medidor; import
 * br.com.apidigitalweb.domin.contratos.Patrimonio; import
 * br.com.apidigitalweb.domin.estoque.AnuncioLoja; import
 * br.com.apidigitalweb.domin.estoque.DescricaoAnuncio; import
 * br.com.apidigitalweb.domin.estoque.FornecedorProduto; import
 * br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio; import
 * br.com.apidigitalweb.domin.estoque.Modelo; import
 * br.com.apidigitalweb.domin.estoque.Produto; import
 * br.com.apidigitalweb.domin.financeiro.AgregadoGrupoFinanceiro; import
 * br.com.apidigitalweb.domin.financeiro.CentroCusto; import
 * br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio; import
 * br.com.apidigitalweb.domin.pessoa.Fornecedor; import
 * br.com.apidigitalweb.domin.pessoa.Funcionario; import
 * br.com.apidigitalweb.enuns.SimNaoEnum; import
 * br.com.apidigitalweb.enuns.StatusActiv; import
 * br.com.apidigitalweb.openfaing.ReceitaWsFeignFornecedor; import
 * br.com.apidigitalweb.repository.AnuncioLojaRepository; import
 * br.com.apidigitalweb.repository.CentroCustoRepository; import
 * br.com.apidigitalweb.repository.ContratoRepository; import
 * br.com.apidigitalweb.repository.FornecedorRepository; import
 * br.com.apidigitalweb.repository.FuncionarioRepository; import
 * br.com.apidigitalweb.repository.GrupoFinanceiroAnuncioRepository; import
 * br.com.apidigitalweb.repository.ModeloRepository; import
 * br.com.apidigitalweb.repository.PatrimonioRepository; import
 * br.com.apidigitalweb.repository.ProdutoRepository;
 * 
 * @EnableFeignClients
 * 
 * @SpringBootApplication public class ApiDigitalApplication implements
 * CommandLineRunner {
 * 
 * public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder();
 * }
 * 
 * @Autowired FuncionarioRepository funcionarioRepository;
 * 
 * @Autowired FornecedorRepository fornecedorRepository;
 * 
 * @Autowired ModeloRepository modeloRepository;
 * 
 * @Autowired ProdutoRepository produtoRepository;
 * 
 * @Autowired CentroCustoRepository centroCustoRepository;
 * 
 * @Autowired AnuncioLojaRepository anuncioLojaRepository;
 * 
 * @Autowired GrupoFinanceiroAnuncioRepository financeiroProdutoLojaRepository;
 * 
 * @Autowired ReceitaWsFeignFornecedor feignFornecedor;
 * 
 * @Autowired PatrimonioRepository patrimonioRepository;
 * 
 * @Autowired ContratoRepository contratoRepository;
 * 
 * @Override public void run(String... args) throws Exception { try { int i =
 * funcionarioRepository.findAll().size(); if
 * (funcionarioRepository.findAll().size() == 0 ||
 * funcionarioRepository.findAll() == null) { Funcionario f = new Funcionario();
 * 
 * f.setNome("M"); java.util.List<String> list = new LinkedList<String>();
 * list.add("Administrador Geral"); f.setRolers("Administrador Geral");
 * f.setEmail("marcelo_macedo01@hotmail.com");
 * f.setStatus(StatusActiv.ATIVO.getDescricao());
 * f.setSenha(encoder().encode("123456")); f = funcionarioRepository.save(f);
 * 
 * } } catch (Exception e) { System.out.println(e); }
 * 
 * }
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(ApiDigitalApplication.class, args); }
 * 
 * }
 */
