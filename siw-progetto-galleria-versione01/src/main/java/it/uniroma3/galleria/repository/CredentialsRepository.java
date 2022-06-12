package it.uniroma3.galleria.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.Credenziali;

public interface CredentialsRepository extends CrudRepository<Credenziali, Long> {
	
public Optional<Credenziali> findByUsername(String username);
	
	/*
	 * Possiamo inserire, in aggiunta ai metodi dell'interfaccia, 
	 * delle query che servono all'applicazione
	*/
	public boolean existsByUsername(String username);

}
