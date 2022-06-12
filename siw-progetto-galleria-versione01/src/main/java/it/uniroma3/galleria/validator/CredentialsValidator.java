package it.uniroma3.galleria.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.galleria.model.Credenziali;
import it.uniroma3.galleria.model.Utente;
import it.uniroma3.galleria.service.CredentialsService;

/**
 * Validator for Credentials
 */
@Component
public class CredentialsValidator implements Validator {

    @Autowired
    private CredentialsService credentialsService;

    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

    @Override
    public void validate(Object o, Errors errors) {
        Credenziali credentials = (Credenziali) o;
        String username = credentials.getUsername().trim();
        String password = credentials.getPassword().trim();

        if (username.isEmpty())
            errors.rejectValue("username", "required");
        else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "size");
        else if (this.credentialsService.getCredentials(username) != null)
            errors.rejectValue("username", "duplicate");

        if (password.isEmpty())
            errors.rejectValue("password", "required");
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            errors.rejectValue("password", "size");
        
        if(this.credentialsService.alreadyExists((Credenziali)o)) {
			// si rejecta la validazione registrando un codice di errore
			errors.reject("credenziali.duplicato"); 
		}
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

}
