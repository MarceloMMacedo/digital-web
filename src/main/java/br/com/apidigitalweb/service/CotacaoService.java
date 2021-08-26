package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.cotacao.ItensCotacao;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.estoque.BaseAnuncio;
import br.com.apidigitalweb.domin.estoque.FornecedorProduto;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto;
import br.com.apidigitalweb.dto.reposicao.ListCotacaoDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.CotacaoRepository;

@Service
public class CotacaoService extends BaseServic<Cotacao> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CotacaoRepository repo;

	@Autowired
	private AnuncioWebService anuncioWebService;

	@Autowired
	private AnuncioLojaService anuncioLojaService;
	@Autowired
	private AnuncioServicoService anuncioServicoService;
	@Autowired
	private AnuncioContratoService anuncioContratoService;

	@Autowired
	private ProdutoService produtoService;

	public Page<ListCotacaoDto> findallpagecotacaodto(Pageable page) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Page<ListCotacaoDto> baseDtos = repo.listAllActive(page).map(x -> new ListCotacaoDto(x));
		return baseDtos;
	}

	@Override
	public void prenew(Cotacao obj) {
		obj.setStatus(StatusActiv.ABERTO.getDescricao());
		 obj.setDataAbertura(new Date());
	}

	public void statusCancelamento(Long id, Date data) {
		Cotacao cotacao = repo.findById(id).get();
		cotacao.setStatus(StatusActiv.INATIVO.getDescricao());
		cotacao.setDataFim(data);
	}

	@Override
	public Cotacao newobj(Cotacao obj) {
		prenew(obj);
		obj = repo.save(obj);
		posNewObj(obj);
		return obj;
	}

	public void ressuprir(Cotacao obj) {
		int qtd = 0;
		obj.setStatus(StatusActiv.QUIT.getDescricao());
		if (obj.getDataFim() == null)
			obj.setDataFim(new Date());

		for (ItensCotacao i : obj.getItensCotacaos()) {

			switch (i.getTipoanuncio()) {
			case "Web":
				AnuncioWeb anuncio = anuncioWebService.fingbyid(i.getAnuncio());
				// atualizar valorfornecedo
				if (anuncio.getItensProduto().size() == 1) {
					for (ItemProdutoAnuncio x : anuncio.getItensProduto()) {
						boolean isfornecedor = false;
						for (FornecedorProduto f : x.getProduto().getFornecedoresproduto()) {

							if (obj.getFornecedor().getId() == f.getFornecedor().getId()) {
								boolean flag = false;
								isfornecedor = true;
								if (f.getValor() > i.getValor()) {
									f.setValor(i.getValor());
									flag = true;
								}
								if (flag) {
									produtoService.saveobj(x.getProduto().getId(), x.getProduto());
								}
								;
							}
						}
						if (isfornecedor) {
							FornecedorProduto fornecedorProduto = new FornecedorProduto(obj.getFornecedor().getNome(),
									obj.getFornecedor(), i.getValor());
							produtoService.saveobj(x.getProduto().getId(), x.getProduto());
						}
					}

				}
				qtd = anuncio.getSaldo() + i.getQtd();
				anuncio.setSaldo(qtd);
				anuncioWebService.saveobj(anuncio.getId(), anuncio);
				break;
			case "Local":
				AnuncioLoja anuncioLoja = anuncioLojaService.fingbyid(i.getAnuncio());
				if (anuncioLoja.getItensProduto().size() == 1) {
					for (ItemProdutoAnuncio x : anuncioLoja.getItensProduto()) {
						boolean isfornecedor = false;
						for (FornecedorProduto f : x.getProduto().getFornecedoresproduto()) {
							if (obj.getFornecedor().getId() == f.getFornecedor().getId()) {
								boolean flag = false;
								isfornecedor = true;
								if (f.getValor() > i.getValor()) {
									f.setValor(i.getValor());
									flag = true;
								}
								if (flag) {
									produtoService.saveobj(x.getProduto().getId(), x.getProduto());
								}
							}
						}
						if (isfornecedor) {
							FornecedorProduto fornecedorProduto = new FornecedorProduto(obj.getFornecedor().getNome(),
									obj.getFornecedor(), i.getValor());
							produtoService.saveobj(x.getProduto().getId(), x.getProduto());
						}
					}

				}
				qtd = anuncioLoja.getSaldo() + i.getQtd();
				anuncioLoja.setSaldo(qtd);
				anuncioLojaService.saveobj(anuncioLoja.getId(), anuncioLoja);
				break;
			case "Contrato":
				AnuncioContrato anuncioServico = anuncioContratoService.fingbyid(i.getAnuncio());
				if (anuncioServico.getItensProduto().size() == 1) {
					for (ItemProdutoAnuncio x : anuncioServico.getItensProduto()) {
						boolean isfornecedor = false;
						for (FornecedorProduto f : x.getProduto().getFornecedoresproduto()) {
							if (obj.getFornecedor().getId() == f.getFornecedor().getId()) {
								boolean flag = false;
								isfornecedor = true;
								if (f.getValor() > i.getValor()) {
									f.setValor(i.getValor());
									flag = true;
								}
								if (flag) {
									produtoService.saveobj(x.getProduto().getId(), x.getProduto());
								}
								;
							}
						}
						if (isfornecedor) {
							FornecedorProduto fornecedorProduto = new FornecedorProduto(obj.getFornecedor().getNome(),
									obj.getFornecedor(), i.getValor());
							produtoService.saveobj(x.getProduto().getId(), x.getProduto());
						}
					}

				}
				qtd = anuncioServico.getSaldo() + i.getQtd();
				anuncioServico.setSaldo(qtd);
				anuncioContratoService.saveobj(anuncioServico.getId(), anuncioServico);
				break;

			default:
				break;
			}
		}

		repo.save(obj);
	}

	public CotacaoDto getCotacao(Long id) {
		return new CotacaoDto(repo.findById(id).get());
	}

	public List<SampleDto> anuncios(String tipo) {
		List<SampleDto> anuncio = new ArrayList<>();
		/*
		 * 
		 * Web(0, ""), Local(1, "") , Servico(2, "Serviço") , Contrato(3, "Contrato") ;
		 */
		switch (tipo) {
		case "Contrato":
			anuncio = anuncioContratoService.getSampleDto();
			break;
		case "Loja":
			anuncio = anuncioLojaService.getSampleDto();
			break;
		case "Web":
			anuncio = anuncioWebService.getSampleDto();
			break;
		default:
			break;
		}
		return anuncio;
	}

	public CotacaoDto addanuncio(Long idcotacao, Long idanuncio, Integer qtd, String tipo) {
		Cotacao c = fingbyid(idcotacao);
		BaseAnuncio anuncio;
		ItensCotacao i = new ItensCotacao();
		switch (tipo) {
		case "Contrato":
			anuncio = anuncioContratoService.fingbyid(idanuncio);
			i.setAnuncio(idanuncio);
			i.setDescricao(anuncio.getNome());
			i.setTipoanuncio(tipo);
			i.setQtd(qtd);
			i.setValorinterno(anuncio.getValorInterno());
			i.setValor(anuncio.getValorInterno());
			i.setUnidade(anuncio.getUnidade());
			c.getItensCotacaos().add(i);

			c = saveobj(c.getId(), c);
			break;
		case "Loja":
			anuncio = anuncioLojaService.fingbyid(idanuncio);
			i.setAnuncio(idanuncio);
			i.setDescricao(anuncio.getNome());
			i.setTipoanuncio(tipo);
			i.setUnidade(anuncio.getUnidade());
			i.setQtd(qtd);
			i.setValorinterno(anuncio.getValorInterno());
			i.setValor(anuncio.getValorInterno());
			c.getItensCotacaos().add(i);
			c = saveobj(c.getId(), c);
			break;
		case "Web":
			anuncio = anuncioWebService.fingbyid(idanuncio);
			i.setAnuncio(idanuncio);
			i.setUnidade(anuncio.getUnidade());
			i.setDescricao(anuncio.getNome());
			i.setTipoanuncio(tipo);
			i.setQtd(qtd);
			i.setValorinterno(anuncio.getValorInterno());
			i.setValor(anuncio.getValorInterno());
			c.getItensCotacaos().add(i);
			c = saveobj(c.getId(), c);
			break;
		default:
			break;
		}
		c = fingbyid(idcotacao);
		return new CotacaoDto(c);
	}

}
