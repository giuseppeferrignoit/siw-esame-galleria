package it.uniroma3.galleria.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.galleria.model.GalleriaArte;

public interface GalleriaArteRepository extends CrudRepository<GalleriaArte, Long> {
	boolean existsByNomeAndDescrizioneAndCitta(String nome, String descrizione, String citta);

}
