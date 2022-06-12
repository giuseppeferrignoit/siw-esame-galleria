package it.uniroma3.galleria.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.galleria.model.Cliente;
import it.uniroma3.galleria.model.GalleriaArte;
import it.uniroma3.galleria.model.Opera;
import it.uniroma3.galleria.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void save(Cliente cliente) { 
		// Il save è di tipo transactional
		clienteRepository.save(cliente);
	}
	
	@Transactional
	public void delete(Cliente cliente) { 
		// Il save è di tipo transactional
		clienteRepository.delete(cliente);
	}
	
	@Transactional
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Cliente findById (Long id) {
		return clienteRepository.findById(id).get();
	}
	
	public List<Cliente> findAll() {
		List<Cliente> clienti = new ArrayList<Cliente>();
		for (Cliente cliente : clienteRepository.findAll()) {
			clienti.add(cliente);
		}
		return clienti;
	}
	/*
	@Transactional
	public void acquistaOpera(Cliente cliente, Opera opera) {
		cliente.getOpere().add(opera);
	}
	*/
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Cliente cliente) {
		return clienteRepository.existsByNomeAndCognomeAndNazionalita
				(cliente.getNome(), cliente.getCognome(), cliente.getNazionalita());
	}
}

