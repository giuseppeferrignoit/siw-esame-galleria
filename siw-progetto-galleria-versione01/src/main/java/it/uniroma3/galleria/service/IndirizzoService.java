package it.uniroma3.galleria.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.galleria.model.Artista;
import it.uniroma3.galleria.model.Indirizzo;
import it.uniroma3.galleria.repository.IndirizzoRepository;

@Service
public class IndirizzoService {
	
	@Autowired
	private IndirizzoRepository indirizzoRepository;
	
	@Transactional
	public void save(Indirizzo indirizzo) { 
		// Il save è di tipo transactional
		indirizzoRepository.save(indirizzo);
	}
	
	@Transactional
	public void delete(Indirizzo indirizzo) { 
		// Il save è di tipo transactional
		indirizzoRepository.delete(indirizzo);
	}
	
	@Transactional
	public void deleteById(Long id) {
		indirizzoRepository.deleteById(id);
	}
	
	public Indirizzo findById (Long id) {
		return indirizzoRepository.findById(id).get();
	}
	
	public List<Indirizzo> findAll() {
		List<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		for (Indirizzo indirizzo : indirizzoRepository.findAll()) {
			indirizzi.add(indirizzo);
		}
		return indirizzi;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Indirizzo indirizzo) {
		return indirizzoRepository.existsByViaAndCittaAndCap
				(indirizzo.getVia(), indirizzo.getCitta(), indirizzo.getCap());
	}

	public Indirizzo creaIndirizzo() {
		return new Indirizzo();
	}
}


