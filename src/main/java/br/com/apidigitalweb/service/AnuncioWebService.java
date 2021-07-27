package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.estoque.DescricaoAnuncio;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.dto.AnuncioLojaDTO;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.repository.AnuncioWebRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;

@Service
public class AnuncioWebService extends BaseServic<AnuncioWeb> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	AnuncioWebRepository repo;

	@Autowired
	ProdutoRepository produtoRepository;

	@Override
	public Page<BaseDto> findallpagedto(String find, Pageable page) {

		Page<BaseDto> baseDtos = findallpage(find, page)
				.map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())));
		return baseDtos;
	}

	@Override
	public Page<AnuncioWeb> findallpage(String find, Pageable page) {
		Page<AnuncioWeb> findallpage = repo.findByNomeContainingIgnoreCase(find, page);
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
	public AnuncioWeb fingbyid(Long id) {
		AnuncioWeb obj = super.fingbyid(id);
		obj.getImagens().stream().forEach(el -> {
			el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
		});
		return obj;
	}

	@Override
	public AnuncioWeb findbynome(String nome) {
		AnuncioWeb obj = super.findbynome(nome);
		obj.getImagens().stream().forEach(el -> {
			el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
		});
		return obj;
	}

	@Override
	public List<AnuncioWeb> fingbynome(String find) {
		// TODO Auto-generated method stub
		List<AnuncioWeb> fingbynome = super.fingbynome(find);
		fingbynome.stream().forEach(x -> {
			x.getImagens().stream().forEach(el -> {
				el.setSrcImagem(downloadFile(el.getImagem() + "." + el.getExtension()));
			});
		});
		return fingbynome;
	}

	@Override
	public AnuncioWeb newobj(AnuncioWeb obj) {
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
	@Override
	public AnuncioWeb saveobj(Long id, AnuncioWeb obj) {
		AnuncioWeb p = repo.findById(id).get();
		obj.setImagem(p.getImagem());
		obj = repo.save(obj);
		return obj;
	}
}
