package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.cotacao.ItensCotacao;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.estoque.FornecedorProduto;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.CotacaoRepository;
import br.com.apidigitalweb.repository.ProdutoRepository;

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
	private ProdutoService produtoService;

	public void statusCancelamento(Long id, Date data) {
		Cotacao cotacao = repo.findById(id).get();
		cotacao.setStatus(StatusActiv.INATIVO.getDescricao());
		cotacao.setDataFim(data);
	}

	public void ressuprir(Cotacao obj) {
		int qtd = 0;
		obj.setStatus(StatusActiv.QUIT.getDescricao());
		if (obj.getDataFim() == null)
			obj.setDataFim(new Date());

		for (ItensCotacao i : obj.getItensCotacaos()) {

			switch (i.getTipoAnuncio()) {
			case "Web":
				AnuncioWeb anuncio = anuncioWebService.fingbyid(i.getAnuncio().getId());
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
				qtd = anuncio.getSaldo() + i.getQuantidade();
				anuncio.setSaldo(qtd);
				anuncioWebService.saveobj(anuncio.getId(), anuncio);
				break;
			case "Local":
				AnuncioLoja anuncioLoja = (AnuncioLoja) i.getAnuncio();
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
						}}

				}
				qtd = anuncioLoja.getSaldo() + i.getQuantidade();
				anuncioLoja.setSaldo(qtd);
				anuncioLojaService.saveobj(anuncioLoja.getId(), anuncioLoja);
				break;
			case "Serviço":
				AnuncioServico anuncioServico = (AnuncioServico) i.getAnuncio();
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
						}}

				}
				qtd = anuncioServico.getSaldo() + i.getQuantidade();
				anuncioServico.setSaldo(qtd);
				anuncioServicoService.saveobj(anuncioServico.getId(), anuncioServico);
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

}
