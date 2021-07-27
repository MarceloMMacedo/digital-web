package br.com.apidigitalweb.enuns;

public enum TipoProdutoEnum {

	Copiadora(0, "Copiadora"), 
	Imprressora(1, "Impressora") ,
	Transformador(2, "Transformador"), 
	Multifuncionallaser(3, "Multifuncional Laser") ,
	MultifuncionalJatoTinta(4, "Multifuncional Jato Tinta"), 
	Outros(5, "Outros")  ;
	 

	private Integer id;
	private String descricao;

	private TipoProdutoEnum(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static String getById(Integer id) {
		for (TipoProdutoEnum e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	}

	public static Integer findById(String s) {
		for (TipoProdutoEnum e : values()) {
			if (e.getDescricao().equals(s))
				return e.getId();
		}
		return 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
