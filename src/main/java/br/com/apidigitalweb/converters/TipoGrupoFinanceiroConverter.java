package br.com.apidigitalweb.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.TipoGrupoFinanceiroEnum;

@Converter
public class TipoGrupoFinanceiroConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return TipoGrupoFinanceiroEnum.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  TipoGrupoFinanceiroEnum.getById(dbData);
	}

	 
}
