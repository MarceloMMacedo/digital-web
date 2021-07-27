package br.com.apidigitalweb.enuns;

public enum ItemOrdemVSCEnum { 
	AnuncioWeb(1,"AnuncioWeb"),  
	AnuncioLoja(2,"AnuncioLoja" ),
	AnuncioContrato(3,"AnuncioContrato"),
	MaoObra(4,"Mão de Obra")  ;

	private int id;
	private String descricao;

	/* 0-AnuncioWeb
	   * 1-AnuncioLoja
	   * 2-AnuncioContrato    */ 

	private ItemOrdemVSCEnum(int valor, String descricao) {
		this.id = valor;
		this.descricao = descricao;
	}

	public static String getById(int id) {
		for (ItemOrdemVSCEnum e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	}

	public static int findById(String s) {
		for (ItemOrdemVSCEnum e : values()) {
			if (e.getDescricao().equals(s) )
				return e.getId();
		}
		return 0;
	}

	 

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 

}
