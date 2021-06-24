package br.com.alugacar.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.ConversionException;
import br.com.caelum.vraptor.converter.ConversionMessage;

@SuppressWarnings("deprecation")
@Specializes
@Convert(Date.class)
@ApplicationScoped
public class DateConverter extends br.com.caelum.vraptor.converter.DateConverter {

	static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date convert(String value, Class<? extends Date> type) {
		if (isNullOrEmpty(value))
			return null;
		try {
			System.out.println("PARSE " + value);
			System.out.println("PARSE " + SDF.parse(value));
			return SDF.parse(value);
		} catch (ParseException e) {
			throw new ConversionException(new ConversionMessage("is_not_a_valid_date", value));
		}
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

}
