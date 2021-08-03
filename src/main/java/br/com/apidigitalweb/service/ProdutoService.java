package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.estoque.ProdutoSampleDto;
import br.com.apidigitalweb.repository.AnuncioContratoRepository;
import br.com.apidigitalweb.repository.AnuncioLojaRepository;
import br.com.apidigitalweb.repository.AnuncioWebRepository;
import br.com.apidigitalweb.repository.FornecedorRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;

@Service
public class ProdutoService extends BaseServic<Produto> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	ProdutoRepository repo;

	@Autowired
	AnuncioLojaRepository anuncioLojaService;

	@Autowired
	AnuncioContratoRepository anuncioContratoService;

	@Autowired
	AnuncioWebRepository anuncioWebService;

	@Override
	public List<BaseDto> getAllsample() {
		// TODO Auto-generated method stub
		return getAll().stream().map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}

	@Override
	public Page<BaseDto> findallpagedto(String find, Pageable page) {

		Page<BaseDto> baseDtos = findallpage(find, page)
				.map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())));
		return baseDtos;
	}

	@Override
	public Produto fingbyid(Long id) {
		String x  ;
		Produto el = super.fingbyid(id);
  
				
		List<Object> lista = repo.listProdutoAnuncioLoja(el.getId());
		el.getSaldosProduto().setSaldoLoja(0);
		el.getSaldosProduto().setSaldoReposicaoLoja(0);
		
		lista.stream().forEach(el1->{
			String x1  = String.valueOf(el1);
			AnuncioLoja anuncioLoja  = anuncioLojaService.findById(Long.valueOf(x1)).get(); 
			try {
				el.getSaldosProduto().setSaldoLoja(el.getSaldosProduto().getSaldoLoja() + anuncioLoja.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoLoja(el.getSaldosProduto().getSaldoReposicaoLoja() +
						anuncioLoja.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			}			
		});
		
		 
		lista = repo.listProdutoAnuncioWeb(el.getId());
		el.getSaldosProduto().setSaldoWeb(0);
		el.getSaldosProduto().setSaldoReposicaoWeb(0);
		lista.stream().forEach(el1->{
			String x1  = String.valueOf(el1);
			AnuncioWeb anuncioWeb  = anuncioWebService.findById(Long.valueOf(x1)).get(); 
			try {
				el.getSaldosProduto().setSaldoWeb(el.getSaldosProduto().getSaldoWeb() + anuncioWeb.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoWeb(el.getSaldosProduto().getSaldoReposicaoWeb() +
						anuncioWeb.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			};
		});

		lista = repo.listProdutoAnuncioContrato(el.getId());
		el.getSaldosProduto().setSaldoContrato(0);
		el.getSaldosProduto().setSaldoReposicaoContrato(0);
		lista.stream().forEach(el1->{
			String x1  = String.valueOf(el1);
			AnuncioContrato anuncioContrato  = anuncioContratoService.findById(Long.valueOf(x1)).get(); 
			try {
				el.getSaldosProduto().setSaldoContrato(el.getSaldosProduto().getSaldoContrato() + anuncioContrato.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoContrato(el.getSaldosProduto().getSaldoReposicaoContrato() +
						anuncioContrato.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		
		return el;
	}

	@Override
	public Produto findbynome(String nome) {
		String x  ;
		Produto el = super.findbynome(nome);
		List<Object> lista = repo.listProdutoAnuncioLoja(el.getId());
		el.getSaldosProduto().setSaldoLoja(0);
		el.getSaldosProduto().setSaldoReposicaoLoja(0);
		for (Object object : lista) {
			x = String.valueOf(object);
			AnuncioLoja anuncioLoja  = anuncioLojaService.findById(Long.valueOf(x)).get();  
			try {
				el.getSaldosProduto().setSaldoLoja(el.getSaldosProduto().getSaldoLoja() + anuncioLoja.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoLoja(el.getSaldosProduto().getSaldoReposicaoLoja() +
						anuncioLoja.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		lista = repo.listProdutoAnuncioWeb(el.getId());
		el.getSaldosProduto().setSaldoWeb(0);
		el.getSaldosProduto().setSaldoReposicaoWeb(0);
		for (Object object : lista) {
			x = String.valueOf(object);
			AnuncioWeb anuncioWeb  = anuncioWebService.findById(Long.valueOf(x)).get();
			try {
				el.getSaldosProduto().setSaldoWeb(el.getSaldosProduto().getSaldoWeb() + anuncioWeb.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoWeb(el.getSaldosProduto().getSaldoReposicaoWeb() +
						anuncioWeb.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		lista = repo.listProdutoAnuncioContrato(el.getId());
		el.getSaldosProduto().setSaldoContrato(0);
		el.getSaldosProduto().setSaldoReposicaoContrato(0);
		for (Object object : lista) {
			x = String.valueOf(object);
			AnuncioContrato anuncioContrato  = anuncioContratoService.findById(Long.valueOf(x)).get();
			try {
				el.getSaldosProduto().setSaldoContrato(el.getSaldosProduto().getSaldoContrato() + anuncioContrato.getSaldo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				el.getSaldosProduto().setSaldoReposicaoContrato(el.getSaldosProduto().getSaldoReposicaoContrato() +
						anuncioContrato.getSaldoReposicao());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return el;
	}

	@Override
	public List<Produto> getAll() {
		
		List<Produto> elx = super.getAll();
		elx.stream().forEach(el -> {
			 
			List<Object> lista = repo.listProdutoAnuncioLoja(el.getId());
			el.getSaldosProduto().setSaldoLoja(0);
			el.getSaldosProduto().setSaldoReposicaoLoja(0);
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioLoja anuncioLoja  = anuncioLojaService.findById(Long.valueOf(x)).get(); 
				try {
					el.getSaldosProduto().setSaldoLoja(el.getSaldosProduto().getSaldoLoja() + anuncioLoja.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoLoja(el.getSaldosProduto().getSaldoReposicaoLoja() +
							anuncioLoja.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			lista = repo.listProdutoAnuncioWeb(el.getId());
			el.getSaldosProduto().setSaldoWeb(0);
			el.getSaldosProduto().setSaldoReposicaoWeb(0);
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioWeb anuncioWeb  = anuncioWebService.findById(Long.valueOf(x)).get();
				try {
					el.getSaldosProduto().setSaldoWeb(el.getSaldosProduto().getSaldoWeb() + anuncioWeb.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoWeb(el.getSaldosProduto().getSaldoReposicaoWeb() +
							anuncioWeb.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			lista = repo.listProdutoAnuncioContrato(el.getId());
			el.getSaldosProduto().setSaldoContrato(0);
			el.getSaldosProduto().setSaldoReposicaoContrato(0);
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioContrato anuncioContrato  = anuncioContratoService.findById(Long.valueOf(x)).get(); 
				try {
					el.getSaldosProduto().setSaldoContrato(el.getSaldosProduto().getSaldoContrato() + anuncioContrato.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoContrato(el.getSaldosProduto().getSaldoReposicaoContrato() +
							anuncioContrato.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		return elx;
	}

	@Override
	public Page<Produto> findallpage(String find, Pageable page) {
 
		Page<Produto> findallpage = repo.findByNomeContainingIgnoreCase(find, page);

		findallpage.getContent().forEach(el -> {
			List<Object> lista = repo.listProdutoAnuncioLoja(el.getId());
			el.getSaldosProduto().setSaldoLoja(0);
			el.getSaldosProduto().setSaldoReposicaoLoja(0);
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioLoja anuncioLoja  = anuncioLojaService.findById(Long.valueOf(x)).get(); 
				try {
					el.getSaldosProduto().setSaldoLoja(el.getSaldosProduto().getSaldoLoja() + anuncioLoja.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoLoja(el.getSaldosProduto().getSaldoReposicaoLoja() +
							anuncioLoja.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			lista = repo.listProdutoAnuncioWeb(el.getId());
			el.getSaldosProduto().setSaldoWeb(0);
			el.getSaldosProduto().setSaldoReposicaoWeb(0);
			
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioWeb anuncioWeb  = anuncioWebService.findById(Long.valueOf(x)).get(); 
				try {
					el.getSaldosProduto().setSaldoWeb(el.getSaldosProduto().getSaldoWeb() + anuncioWeb.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoWeb(el.getSaldosProduto().getSaldoReposicaoWeb() +
							anuncioWeb.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			lista = repo.listProdutoAnuncioContrato(el.getId());
			el.getSaldosProduto().setSaldoContrato(0);
			el.getSaldosProduto().setSaldoReposicaoContrato(0);
			for (Object object : lista) {
				String x  ;
				x = String.valueOf(object);
				AnuncioContrato anuncioContrato  = anuncioContratoService.findById(Long.valueOf(x)).get();
				try {
					el.getSaldosProduto().setSaldoContrato(el.getSaldosProduto().getSaldoContrato() + anuncioContrato.getSaldo());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					el.getSaldosProduto().setSaldoReposicaoContrato(el.getSaldosProduto().getSaldoReposicaoContrato() +
							anuncioContrato.getSaldoReposicao());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		/*
		 * findallpage.getContent().stream().forEach(el->{ el.setImagem(
		 * downloadFile(el.getImagem() + "." + el.getExtension())); });
		 */
		return findallpage;
	}

 
	@Override
	public List<?> listany() {
		List<Produto> list = repo.findAll();
		List<ProdutoSampleDto> dtos = new ArrayList<ProdutoSampleDto>();
		dtos = list.stream().map(x -> new ProdutoSampleDto(x)).collect(Collectors.toList());

		return dtos;
	}
}
