
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
 
			CentroCusto cc = new CentroCusto(1.8, 2.6, 3.9, "Centro1");
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
			Modelo f = new Modelo();
			if (modeloRepository.findAll().size() == 0 || modeloRepository.findAll() == null) {

				f.setNome("7435");
				f = modeloRepository.save(f);
				f = new Modelo();
				f.setNome("7485");
				f = modeloRepository.save(f);
			}

			if (produtoRepository.findAll().size() == 0 || produtoRepository.findAll() == null) {
				Produto p = new Produto();

				p.setNome("M");
				p.setModelo(f);
				List<String> categorias = new ArrayList<>();

				categorias.add("c1");
				categorias.add("c2");
				categorias.add("c31");
				// p.setCategoriafonrs(categorias);

				p.setImagem("2688c202-3b2f-4ec9-ad28-375fd9a33cc5");
				p.setExtension("jpg");
				p = produtoRepository.save(p);

				Cliente c = new Cliente();

				BasePessoaJuridicaDTO t = new BasePessoaJuridicaDTO();

				t = feignFornecedor.buscaPorcnpj("08054004000164");
				Endereco e = new Endereco(t.getCep(), t.getLogradouro(), t.getComplemento(), t.getBairro(),
						t.getMunicipio(), t.getNumero(), t.getUf());

				c = new Cliente(t, e);

				c.setRolers("Administrador Geral");
				c.setEmail(UUID.randomUUID().toString());
				c.setStatus(StatusActiv.ATIVO.getDescricao());
				c.setSenha(encoder().encode("123456"));
				c = clienteRepository.save(c);

				AnuncioLoja a;
				List<FornecedorProduto> fornecedorProdutos = new ArrayList<FornecedorProduto>();
				FornecedorProduto fp = new FornecedorProduto();

				Fornecedor fo = new Fornecedor();
				java.util.List<String> list = new LinkedList<String>();
				list.add("Administrador Geral");
				t = new BasePessoaJuridicaDTO();

				t = feignFornecedor.buscaPorcnpj("08054004000164");
				e = new Endereco(t.getCep(), t.getLogradouro(), t.getComplemento(), t.getBairro(), t.getMunicipio(),
						t.getNumero(), t.getUf());

				fo = new Fornecedor(t, e);

				fo.setRolers("Administrador Geral");
				fo.setEmail(UUID.randomUUID().toString());
				fo.setStatus(StatusActiv.ATIVO.getDescricao());
				fo.setSenha(encoder().encode("123456"));
				fo = fornecedorRepository.save(fo);

				List<Produto> listProdutos = new ArrayList<>();
				for (int j = 0; j < 100; j++) {
					fornecedorProdutos.clear();
					a = new AnuncioLoja();
					a.setNome("Anuncio Loja - " + String.valueOf(j));

					p = new Produto();
					p.setNome("Produto - " + String.valueOf(j));
					p.setModelo(f);
					categorias = new ArrayList<>();

					p.setIsvalue(SimNaoEnum.Sim.getDescricao());
					categorias.add("c1");
					categorias.add("c2");
					categorias.add("c31");

					for (int j2 = 0; j2 < 5; j2++) {
						fornecedorProdutos.add(new FornecedorProduto("fornecedor1" + String.valueOf(j2), fo, 1.36));
					}
					p.setFornecedoresproduto(fornecedorProdutos);
					// p.setCategorias(categorias);
					p.setImagem("a2e07eef-2784-4c9a-a9a2-ed36f785f868");
					p.setExtension("png");

					listProdutos.add(p);

				}

				produtoRepository.saveAll(listProdutos);
				List<AnuncioLoja> anuncioLojas = new ArrayList<>();
				for (int j = 1; j < 100; j++) {
					a = new AnuncioLoja();
					Produto k = produtoRepository.findById((long) j).get();

					ItemProdutoAnuncio itemProdutoAnuncio = new ItemProdutoAnuncio(k, 1.0, k.getValorfinal(),
							k.getDescricao());
					List<ItemProdutoAnuncio> itensProduto = new ArrayList<>();
					a.setNome("Anuncio Loja - " + String.valueOf(j));
					a.setImagem("a2e07eef-2784-4c9a-a9a2-ed36f785f868");
					for (int j2 = 0; j2 < 5; j2++) {
						// String string = args[j2];a
						itensProduto.add(new ItemProdutoAnuncio(k, 1.0, k.getValorfinal() + j2, k.getDescricao()));
						a.getImagens().add(new ListaImagens("a2e07eef-2784-4c9a-a9a2-ed36f785f868", "png"));
					}
					k = produtoRepository.findById((long) 1).get();
					itensProduto.add(new ItemProdutoAnuncio(k, 1.0, k.getValorfinal() + 2, k.getDescricao()));
					a.setExtension("png");
					a.setSaldo(j);
					a.setSaldo(j + 100);
					a.setSaldoMinimo(j + 5);
					// a.setGrupoPreco(g);
					a.setItensProduto(itensProduto);
					a.setGrupopreco(gl);
					List<DescricaoAnuncio> descricoes = new ArrayList<DescricaoAnuncio>();
					descricoes.add(new DescricaoAnuncio("Peso", String.valueOf(k.getPeso())));
					descricoes.add(new DescricaoAnuncio("Altura", String.valueOf(k.getAltura())));
					descricoes.add(new DescricaoAnuncio("Largura", String.valueOf(k.getLargura())));
					descricoes.add(new DescricaoAnuncio("Unidade", String.valueOf(k.getUnidade())));
					/*
					 * obj.setPeso(p.getPeso()); obj.setAltura(p.getAltura());
					 * obj.setLargura(p.getLargura()); obj.setUnidade(p.getUnidade());
					 */
					a.setDescricao(k.getDescricao());
					a.setDescricoes(descricoes);
					anuncioLojas.add(a);
				}
				anuncioLojaRepository.saveAll(anuncioLojas);

				Patrimonio pa = new Patrimonio();

				pa.setNome("patrimonio1");
				Medidor me = new Medidor();
				me.setMedidorA3Final(1);
				
				me.setMedidorA4Final(12);
				me.setMedidorA4Inicial(51);

				pa.setMedidoresInicial(me);
				pa.setStatus(StatusActiv.ATIVO.getDescricao());
				pa.setMedidorServico(me);
				pa.setMedidorContrato(me);
				pa.setSerial("123456");
				pa.setModelo(f);
				patrimonioRepository.save(pa);

				pa = new Patrimonio();
				pa.setNome("patrimonio2");
				me = new Medidor();
				pa.setStatus(StatusActiv.ATIVO.getDescricao());
				me.setMedidorA3Final(1);
				me.setMedidorA4Final(12);
				me.setMedidorA4Inicial(51);

				pa.setMedidoresInicial(me);
				pa.setMedidorServico(me);
				pa.setMedidorContrato(me);
				pa.setSerial("123456");
				pa.setModelo(f);
				patrimonioRepository.save(pa);
				Contrato contrato = new Contrato();
				contrato.setNome("ContratoZenir");
				contrato.setDataInicio(new Date());
				contrato.setPeriodo(12);

				ItensContratoPatrimonio itensContratoPatrimonio = new ItensContratoPatrimonio(pa, 0.25, "Sim", 1000,
						250.0);
				contrato.getItenspatrimonio().add(itensContratoPatrimonio);
				contrato.getItenspatrimonio().add(itensContratoPatrimonio);
				contrato.getItenspatrimonio().add(itensContratoPatrimonio);
				contrato.getItenspatrimonio().add(itensContratoPatrimonio);
				pa = new Patrimonio();
				pa = patrimonioRepository.findById((long) 2).get();
				itensContratoPatrimonio = new ItensContratoPatrimonio(pa, 0.25, "Sim", 1000, 250.0);
				contrato.getItenspatrimonio().add(itensContratoPatrimonio);
				contratoRepository.save(contrato);
				
				
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

				  c = crepo.findById((long) 1).get();
				  
				  OrdemVenda ov=new OrdemVenda();
				  ov=ordemVendasRepository.save(ov);

				ast.setStatus(StatusActiv.ABERTO.getDescricao());		
				ast.setCliente(c);
				ast.setValor(251);
				ast.setOrdemVenda(ov);
				ast.setCentroCustoFaturas(centroCustoFaturas);
				ast.setBanco(banco);		
				fatuaraVendasRepository.save(ast);
				
				ast = new FaturaVenda();
				ast.setStatus(StatusActiv.ABERTO.getDescricao());
				ast.setCliente(c);
				ast.setValor(-23);
				ast.setOrdemVenda(ov);
				ast.setCentroCustoFaturas(centroCustoFaturas);
				ast.setBanco(banco);		
				fatuaraVendasRepository.save(ast);
				
				ast = new FaturaVenda();
				ast.setStatus(StatusActiv.ABERTO.getDescricao());
				ast.setCliente(c);
				ast.setOrdemVenda(ov);
				ast.setValor(252);
				ast.setCentroCustoFaturas(centroCustoFaturas);
				ast.setBanco(banco);		
				fatuaraVendasRepository.save(ast);
				
				ast = new FaturaVenda();
				ast.setStatus(StatusActiv.ABERTO.getDescricao());
				ast.setCliente(c);
				ast.setValor(25);
				ast.setOrdemVenda(ov);
				ast.setBanco(banco);		
				fatuaraVendasRepository.save(ast);
				
				ast = new FaturaVenda();
				ast.setStatus(StatusActiv.ABERTO.getDescricao());
				ast.setCliente(c);
				ast.setValor(-251);
				ast.setOrdemVenda(ov);
				ast.setCentroCustoFaturas(centroCustoFaturas);
				ast.setBanco(banco);
				fatuaraVendasRepository.save(ast);
				
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
