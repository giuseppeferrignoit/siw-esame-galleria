package it.uniroma3.galleria.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.galleria.model.Cliente;
import it.uniroma3.galleria.service.ClienteService;


@Component
public class ClienteValidator implements Validator {

	@Autowired
	private ClienteService clienteService;

	// Una specifica validazione per non duplicati
	@Override
	public void validate(Object o, Errors errors) { // (1)Oggetto da validare (2)esito validazione
		if(this.clienteService.alreadyExists((Cliente)o)) {
			// si rejecta la validazione registrando un codice di errore
			errors.reject("cliente.duplicato"); 
		}
	}
	
	// Indica quale è la classe su cui facciamo le validazioni
	@Override
	public boolean supports(Class<?> aClass) {
		return Cliente.class.equals(aClass);
	}
}