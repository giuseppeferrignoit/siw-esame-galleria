package it.uniroma3.galleria.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.Indirizzo;

public interface IndirizzoRepository extends CrudRepository<Indirizzo, Long> {
	boolean existsByViaAndCittaAndCapAndPaese(String via, String citta, String cap, String paese);

}
