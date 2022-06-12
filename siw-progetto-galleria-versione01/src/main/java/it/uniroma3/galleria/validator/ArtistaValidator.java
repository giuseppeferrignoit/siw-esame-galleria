package it.uniroma3.galleria.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.galleria.model.Artista;
import it.uniroma3.galleria.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator {

	@Autowired
	private ArtistaService artistaService;

	// Una specifica validazione per non duplicati
	@Override
	public void validate(Object o, Errors errors) { // (1)Oggetto da validare (2)esito validazione
		if(this.artistaService.alreadyExists((Artista)o)) {
			// si rejecta la validazione registrando un codice di errore
			errors.reject("artista.duplicato"); 
		}
	}
	
	// Indica quale è la classe su cui facciamo le validazioni
	@Override
	public boolean supports(Class<?> aClass) {
		return Artista.class.equals(aClass);
	}
}