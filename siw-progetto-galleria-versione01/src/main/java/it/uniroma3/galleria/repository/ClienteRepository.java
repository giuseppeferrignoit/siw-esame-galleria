package it.uniroma3.galleria.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
}
