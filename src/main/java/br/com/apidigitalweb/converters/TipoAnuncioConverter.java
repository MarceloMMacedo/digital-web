package br.com.apidigitalweb.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.TipoAnuncioEnum;

@Converter
public class TipoAnuncioConverter implements AttributeConverter<String, Integer> {

	@Override
	public Integer convertToDatabaseColumn(String attribute) {
		return TipoAnuncioEnum.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) {
		return TipoAnuncioEnum.getById(dbData);
	}

}
