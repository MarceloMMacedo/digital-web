package br.com.apidigitalweb.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFornecedor;

@Converter
public class TipoFornecedorConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return TipoFornecedor.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  TipoFornecedor.getById(dbData);
	}

	 
}
