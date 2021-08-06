package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.repository.HistoricoContaPagarRepository;

@Service
public class HistoricoContaPagarService extends BaseServic<HistoricoContaPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	HistoricoContaPagarRepository repo;

 
	public List<SampleDto> getSampleDtogetgrupo(Long id) {

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		return repo.findAllByGrupocontaspagarId(id).stream().map(x -> new SampleDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}
@Override
public void prenew(HistoricoContaPagar obj) {
	GrupoContaPagar  g=new GrupoContaPagar();
	g.setId(obj.getGrupocontaspagardto().getId());
	obj.setGrupocontaspagar(g);
}
}
