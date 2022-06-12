package it.uniroma3.galleria.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long> {
	boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);

}
