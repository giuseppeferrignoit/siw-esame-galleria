package it.uniroma3.galleria.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.galleria.model.GalleriaArte;
import it.uniroma3.galleria.service.GalleriaArteService;

@Component
public class GalleriaArteValidator implements Validator {

	@Autowired
	private GalleriaArteService galleriaService;

	// Una specifica validazione per non duplicati
	@Override
	public void validate(Object o, Errors errors) { // (1)Oggetto da validare (2)esito validazione
		if(this.galleriaService.alreadyExists((GalleriaArte)o)) {
			// si rejecta la validazione registrando un codice di errore
			errors.reject("galleria.duplicata"); 
		}
	}
	
	// Indica quale è la classe su cui facciamo le validazioni
	@Override
	public boolean supports(Class<?> aClass) {
		return GalleriaArte.class.equals(aClass);
	}
}