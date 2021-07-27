package br.com.apidigitalweb.enuns;

import lombok.Data;
 
public enum EnumCategoriaProduto { 
	ENGRENAGEM("Engrenagem", 0),
	TONER("Toner",1),
	TONERCOLORCIAN("Toner Cian",8),
	TONERCOLORMAGENTA("Toner Magenta",9),
	TONERCOLORAMARELO("Toner Amarelo",10),
	TONERCOLORPRETO("Toner Preto",11),
	CILINDRO("Cilindro",2),
	CHIP("Chip",3),
	CARTUCHO("Cartucho",4) ,
	LAMINALIMPEZA("Lâmina Limpeza",5),
	LAMINADOSADORA("Lâmina Dosadora",6),
	EIXOMAGNETICO("Eixo Magnético",7);


	public String getDescricao() {
		return descricao;
	}


	public int getId() {
		return id;
	}
	private final String descricao;
	
	private final int id;

	private EnumCategoriaProduto(String descricao, int id) {
		this.descricao = descricao;
		this.id = id;
	}

	 
	public static String getById(Integer id) {
		for (EnumCategoriaProduto e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	} 
	public static int findById(String s) {
		for (EnumCategoriaProduto e : values()) {
			if (e.getDescricao().equals(s) )
				return e.getId();
		}
		return 0;
	}

	
}
