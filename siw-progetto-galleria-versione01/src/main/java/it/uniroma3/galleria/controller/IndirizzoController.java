package it.uniroma3.galleria.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.galleria.model.Indirizzo;
import it.uniroma3.galleria.service.ClienteService;
import it.uniroma3.galleria.service.IndirizzoService;
import it.uniroma3.galleria.validator.IndirizzoValidator;



@Controller
public class IndirizzoController {

	@Autowired
	private IndirizzoService indirizzoService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private IndirizzoValidator indirizzoValidator;

	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	 */

	// METODO POST PER INSERIRE UN NUOVO BUFFET

	@PostMapping("/cliente/{idCliente}/indirizzo")
	public String addIndirizzo(@Valid @ModelAttribute(value="indirizzo") Indirizzo indirizzo, 
			@PathVariable("idCliente") Long idCliente,
			BindingResult bindingResult, Model model) {

		/* Se non ci sono errori inserisce la ricorrenza di Indirizzo 
		 * tramite la save del service 
		 * */

		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.indirizzoValidator.validate(indirizzo, bindingResult);

		if (!bindingResult.hasErrors()) {
			
			this.indirizzoService.save(indirizzo); // salvo un oggetto Indirizzo
			this.clienteService.addIndirizzoToCliente(idCliente, indirizzo);
			model.addAttribute("cliente", clienteService.findById(idCliente));

			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla pagina di visualizzazione dati inseriti
			return "admin/cliente.html"; 
		}
		else {
			model.addAttribute("indirizzo", indirizzo);
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/indirizzoForm.html"; 
		}
	}

	// METODI PER DELETE

	@GetMapping("/confermaDeleteIndirizzo/{id}")
	public String confermaDeleteIndirizzo(@PathVariable("id") Long id, Model model) {
		this.indirizzoService.deleteById(id);
		model.addAttribute("indirizzi", this.indirizzoService.findAll());
		return "indirizzo.html";
	}

	@GetMapping("/deleteIndirizzo/{id}")
	public String deleteIndirizzo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("indirizzo", this.indirizzoService.findById(id));
		return "deleteIndirizzo.html";
	}

	// METODI GET

	// richiede un singolo Artista tramite id
	@GetMapping("/indirizzo/{id}")
	public String getIndirizzo(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Indirizzo indirizzo = indirizzoService.findById(id);
		model.addAttribute("indirizzo", indirizzo);
		// ritorna la pagina con i dati dell'entità richiesta
		return "indirizzo.html";
	}

	// richiede un singolo artista tramite id per l'utente semplice
	@GetMapping("/indirizzoUtente/{id}")
	public String getIndirizzoUtente(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Indirizzo indirizzo = indirizzoService.findById(id);
		model.addAttribute("indirizzo", indirizzo);
		// ritorna la pagina con i dati dell'entità richiesta
		return "indirizzoUtente.html";
	}

	// richiede tutti gli Artisti, non c'è id
	@GetMapping("/indirizzi")
	public String getIndirizzi(Model model) {
		List<Indirizzo> indirizzi = indirizzoService.findAll();
		model.addAttribute("indirizzi", indirizzi);
		return "indirizzi.html";
	}

	// richiede tutti gli artisti per l'utente semplice, non c'è id
	@GetMapping("/indirizziUtente")
	public String getIndirizziUtente(Model model) {
		List<Indirizzo> indirizzi = indirizzoService.findAll();
		model.addAttribute("indirizzi", indirizzi);
		return "indirizziUtente.html";
	}

	@GetMapping("/indirizzoForm")
	public String indirizzoForm(Model model) {
		model.addAttribute("indirizzo", new Indirizzo());
		return "admin/indirizzoForm.html";
	}

}
