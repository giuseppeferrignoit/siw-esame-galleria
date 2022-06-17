package it.uniroma3.galleria.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.galleria.model.Artista;
import it.uniroma3.galleria.model.Cliente;
import it.uniroma3.galleria.model.GalleriaArte;
import it.uniroma3.galleria.model.Opera;
import it.uniroma3.galleria.repository.ArtistaRepository;
import it.uniroma3.galleria.repository.OperaRepository;

@Service
public class OperaService {
	
	@Autowired
	private OperaRepository operaRepository;
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public void save(Opera opera, Artista artista) { 
		// Il save è di tipo transactional
		opera.setArtista(artista);
		artista.addOpera(opera);
		artistaRepository.save(artista);
	}
	
	@Transactional
	public void delete(Opera opera) { 
		// Il save è di tipo transactional
		operaRepository.delete(opera);
	}
	
	@Transactional
	public void deleteById(Long id) {
		operaRepository.deleteById(id);
		
	}
	
	@Transactional
	public void setGalleria(GalleriaArte galleria, Opera opera) {
		opera.setGallery(galleria);
	}
	
	@Transactional
	public void setGalleriaNull(GalleriaArte galleria, Opera opera) {
		opera.setGallery(null);
	}
	
	public Opera findById (Long id) {
		return operaRepository.findById(id).get();
	}
	
	public List<Opera> findAll() {
		List<Opera> opere = new ArrayList<Opera>();
		for (Opera opera : operaRepository.findAll()) {
			opere.add(opera);
		}
		return opere;
	}
	
	public List<Opera> findAllByGalleriaArte(GalleriaArte galleria) {
		List<Opera> opere = new ArrayList<Opera>();
		for(Opera opera : operaRepository.findAllByGallery(galleria))
			opere.add(opera);
		return opere;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Opera opera) {
		//return operaRepository.existsByNome(opera.getNome());
		return operaRepository.existsByNomeAndDescrizione(opera.getNome(), opera.getDescrizione());	
	}
	
	public List<Opera> findOpereNonAssociate() {
		List<Opera> opere = this.findAll();
		for (Opera opera : operaRepository.findAll()) {
			if (opera.getGallery() != null || opera.getCliente() != null) // Opera non associata
				opere.remove(opera);
		}
		return opere;
	}

	public void removeGalleriaFromOpere(GalleriaArte galleria) {
		List<Opera> opere = this.findAll();
		for(Opera opera : opere) {
			if(opera.getGallery() != null) {
				if(opera.getGallery().equals(galleria)) {
					opera.setGallery(null);
					this.save(opera, opera.getArtista());
				}
			}
		}
	}

	public Object findOpereNonAcquistate() {
		List<Opera> opere = new ArrayList<>();
		for(Opera opera : operaRepository.findAll()) {
			if(opera.getCliente() == null)
				opere.add(opera);
		}
		return opere;
	}

	public void removeClienteFromOpere(Cliente cliente) {
		List<Opera> opere = this.findAll();
		for(Opera opera : opere) {
			if(opera.getCliente() != null) {
				if(opera.getCliente().equals(cliente)) {
					opera.setCliente(null);
					this.save(opera, opera.getArtista());
				}
			}
		}
	}

}