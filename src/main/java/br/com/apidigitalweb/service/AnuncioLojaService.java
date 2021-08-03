package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.DescricaoAnuncio;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.dto.AnuncioLojaDTO;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.repository.AnuncioLojaRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;

@Service
public class AnuncioLojaService extends BaseServic<AnuncioLoja> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	AnuncioLojaRepository repo;

	@Autowired
	ProdutoRepository produtoRepository;

	@Override
	public Page<BaseDto> findallpagedto(String find, Pageable page) {

		Page<BaseDto> baseDtos = findallpage(find, page)
				.map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())));
		return baseDtos;
	}

	@Override
	public Page<AnuncioLoja> findallpage(String find, Pageable page) {
		Page<AnuncioLoja> findallpage = repo.findByNomeContainingIgnoreCase(find, page);
		findallpage.getContent().stream().forEach(x -> {
			x.getImagens().stream().forEach(el -> {
				el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
			});
		});
		return findallpage;
	}

	public AnuncioLojaDTO newobj(AnuncioLojaDTO obj) {
		// TODO Auto-generated method stub
		return new AnuncioLojaDTO();
	}

	@Override
	public AnuncioLoja fingbyid(Long id) {
		AnuncioLoja obj = super.fingbyid(id);
		obj.getImagens().stream().forEach(el -> {
			el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
		});
		return obj;
	}

	@Override
	public AnuncioLoja findbynome(String nome) {
		AnuncioLoja obj = super.findbynome(nome);
		obj.getImagens().stream().forEach(el -> {
			el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
		});
		return obj;
	}

	@Override
	public List<AnuncioLoja> fingbynome(String find) {
		// TODO Auto-generated method stub
		List<AnuncioLoja> fingbynome = super.fingbynome(find);
		fingbynome.stream().forEach(x -> {
			x.getImagens().stream().forEach(el -> {
				el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
			});
		});
		return fingbynome;
	}

	@Override
	public AnuncioLoja newobj(AnuncioLoja obj) {
		try {

			Produto p = produtoRepository.findById(obj.getItensProduto().get(0).getProduto().getId()).get();

			obj.setImagem(p.getImagem());
			obj.setExtension(p.getExtension());

			List<DescricaoAnuncio> descricoes = new ArrayList<DescricaoAnuncio>();
			descricoes.add(new DescricaoAnuncio("Peso", String.valueOf(p.getPeso())));
			descricoes.add(new DescricaoAnuncio("Peso", String.valueOf(p.getPeso())));
			descricoes.add(new DescricaoAnuncio("Altura", String.valueOf(p.getAltura())));
			descricoes.add(new DescricaoAnuncio("Largura", String.valueOf(p.getLargura())));
			descricoes.add(new DescricaoAnuncio("Unidade", String.valueOf(p.getUnidade())));
			/*
			 * obj.setPeso(p.getPeso()); obj.setAltura(p.getAltura());
			 * obj.setLargura(p.getLargura()); obj.setUnidade(p.getUnidade());
			 */
			obj.setDescricao(p.getDescricao());
			obj.setDescricoes(descricoes);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return super.newobj(obj);
	}
 
}
