package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.AnuncioServicoRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;

@Service
public class AnuncioServicoService extends BaseServic<AnuncioServico> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	AnuncioServicoRepository repo;

	@Autowired
	ProdutoRepository produtoRepository;

	@Override
	public Page<BaseDto> findallpagedto(String find, Pageable page) {

		Page<BaseDto> baseDtos = findallpage(find, page)
				.map(x -> {
					BaseDto b=	new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension()));
					b.setValor(x.getValorFinal());
					return b;
				}
				);

		return baseDtos;
	}
	@Override
	public void prenew(AnuncioServico obj) {
		try {
			obj.setIsPrecificado(SimNaoEnum.Sim.getDescricao());
			obj.setStatus(StatusActiv.ATIVO.getDescricao());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public List<BaseDto> getAllsample() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		return repo.findAll().stream().map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}
	@Override
	public Page<AnuncioServico> findallpage(String find, Pageable page) {
		Page<AnuncioServico> findallpage = repo.findByNomeContainingIgnoreCase(find, page);
		findallpage.getContent().stream().forEach(x -> {
			x.setImagemView(downloadFile(x.getImagem() + "." + x.getExtension()));

		});
		return findallpage;
	}

	@Override
	public AnuncioServico fingbyid(Long id) {
		AnuncioServico obj = super.fingbyid(id);
		obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));

		return obj;
	}

	@Override
	public AnuncioServico findbynome(String nome) {
		AnuncioServico obj = super.findbynome(nome);
		obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));

		return obj;
	}

	@Override
	public List<AnuncioServico> fingbynome(String find) {
		// TODO Auto-generated method stub
		List<AnuncioServico> fingbynome = super.fingbynome(find);
		fingbynome.stream().forEach(x -> {
			obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));

		});
		return fingbynome;
	}

	 
 
}
