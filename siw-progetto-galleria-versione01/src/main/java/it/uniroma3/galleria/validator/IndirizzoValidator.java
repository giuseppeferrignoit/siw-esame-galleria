package it.uniroma3.galleria.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.galleria.model.Indirizzo;
import it.uniroma3.galleria.service.IndirizzoService;

@Component
public class IndirizzoValidator implements Validator {

	@Autowired
	private IndirizzoService indirizzoService;

	// Una specifica validazione per non duplicati
	@Override
	public void validate(Object o, Errors errors) { // (1)Oggetto da validare (2)esito validazione
		if(this.indirizzoService.alreadyExists((Indirizzo)o)) {
			// si rejecta la validazione registrando un codice di errore
			errors.reject("indirizzo.duplicato"); 
		}
	}
	
	// Indica quale è la classe su cui facciamo le validazioni
	@Override
	public boolean supports(Class<?> aClass) {
		return Indirizzo.class.equals(aClass);
	}
}