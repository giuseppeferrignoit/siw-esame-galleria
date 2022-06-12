package it.uniroma3.galleria.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.galleria.model.GalleriaArte;
import it.uniroma3.galleria.model.Opera;
import it.uniroma3.galleria.repository.GalleriaArteRepository;

@Service
public class GalleriaArteService {
	
	@Autowired
	private GalleriaArteRepository galleriaRepository;
	
	@Transactional
	public void save(GalleriaArte galleria) { 
		// Il save è di tipo transactional
		galleriaRepository.save(galleria);
	}
	
	@Transactional
	public void delete(GalleriaArte galleria) { 
		// Il save è di tipo transactional
		galleriaRepository.delete(galleria);
	}
	
	@Transactional
	public void deleteById(Long id) {
		galleriaRepository.deleteById(id);
	}
	
	@Transactional
	public void addOpera(GalleriaArte galleria, Opera opera) {
		galleria.getOpere().add(opera);
	}
	
	@Transactional
	public void removeOpera(GalleriaArte galleria, Opera opera) {
		galleria.getOpere().remove(opera);
	}
	
	public GalleriaArte findById (Long id) {
		return galleriaRepository.findById(id).get();
	}
	
	public List<GalleriaArte> findAll() {
		List<GalleriaArte> gallerie = new ArrayList<GalleriaArte>();
		for (GalleriaArte galleria : galleriaRepository.findAll()) {
			gallerie.add(galleria);
		}
		return gallerie;
	}
	
	public List<Opera> findOpereNotInGalleria(GalleriaArte galleria) {
		List<Opera> opere = galleria.getOpere();
		for (Opera opera : galleria.getOpere())
			opere.remove(opera);
		return opere;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(GalleriaArte galleria) {
		return galleriaRepository.existsByNomeAndDescrizioneAndCitta
				(galleria.getNome(), galleria.getDescrizione(), galleria.getCitta());
	}
}

