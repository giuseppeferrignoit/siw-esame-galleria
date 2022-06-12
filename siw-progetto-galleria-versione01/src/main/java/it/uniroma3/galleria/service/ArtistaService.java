package it.uniroma3.galleria.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.galleria.model.Artista;
import it.uniroma3.galleria.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public void save(Artista artista) { 
		// Il save è di tipo transactional
		artistaRepository.save(artista);
	}
	
	@Transactional
	public void delete(Artista artista) { 
		// Il save è di tipo transactional
		artistaRepository.delete(artista);
	}
	
	@Transactional
	public void deleteById(Long id) {
		artistaRepository.deleteById(id);
	}
	
	public Artista findById (Long id) {
		return artistaRepository.findById(id).get();
	}
	
	public List<Artista> findAll() {
		List<Artista> artisti = new ArrayList<Artista>();
		for (Artista a : artistaRepository.findAll()) {
			artisti.add(a);
		}
		return artisti;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Artista artista) {
		return artistaRepository.existsByNomeAndCognomeAndNazionalita
				(artista.getNome(), artista.getCognome(), artista.getNazionalita());
	}
}

