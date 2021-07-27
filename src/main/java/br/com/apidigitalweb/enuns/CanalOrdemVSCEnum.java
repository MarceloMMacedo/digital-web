package br.com.apidigitalweb.enuns;

public enum CanalOrdemVSCEnum { 
	WEB(1,"WEB"),  
	ONSITE(3,"ONSITE" ),
	TELEFONE(4,"TELEFONE"),  
	WHATSAPP(6,"WHATSAPP"),
	OUTROS (7,"OUTROS") ;
	/**
	 * 
	 * 0-WEB 1-ONSITE 2-TELEFONE 4-WHATSAPP 5-OUTROS
	 */
	private int id;
	private String descricao;

	 

	private CanalOrdemVSCEnum(int valor, String descricao) {
		this.id = valor;
		this.descricao = descricao;
	}

	public static String getById(int id) {
		for (CanalOrdemVSCEnum e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	}

	public static int findById(String s) {
		for (CanalOrdemVSCEnum e : values()) {
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
