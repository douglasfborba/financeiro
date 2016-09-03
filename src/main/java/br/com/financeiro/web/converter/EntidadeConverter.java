package br.com.financeiro.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.financeiro.entidade.Entidade;
import br.com.financeiro.entidade.EntidadeRN;

@FacesConverter(forClass = Entidade.class)
public class EntidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				EntidadeRN entidadeRN = new EntidadeRN();
				return entidadeRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException(
						"Não foi possível encontrar a entidade de código " + value + ". " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Entidade entidade = (Entidade) value;
			return entidade.getCodigo().toString();
		}
		return "";
	}
}
