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

import it.uniroma3.galleria.model.Artista;
import it.uniroma3.galleria.service.ArtistaService;
import it.uniroma3.galleria.service.OperaService;
import it.uniroma3.galleria.validator.ArtistaValidator;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private ArtistaValidator artistaValidator;

	@Autowired
	private OperaService operaService;

	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	 */

	// METODO POST PER INSERIRE UN NUOVO ARTISTA

	@PostMapping("/artista")
	public String addArtista(@Valid @ModelAttribute(value="artista") Artista artista, 
			BindingResult bindingResult, Model model) {

		/* Se non ci sono errori inserisce la ricorrenza di Artista 
		 * tramite la save del service 
		 * */

		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.artistaValidator.validate(artista, bindingResult);

		if (!bindingResult.hasErrors()) {

			this.artistaService.save(artista); // salvo un oggetto Artista
			model.addAttribute("artista", artista);

			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla pagina di visualizzazione dati inseriti
			return "admin/artista.html"; 
		}
		else {
			model.addAttribute("artista", artista);
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/artistaForm.html"; 
		}
	}


	// METODI PER DELETE

	@GetMapping("/confermaDeleteArtista/{id}")
	public String confermaDeleteArtista(@PathVariable("id") Long id, Model model) {
		this.artistaService.deleteById(id);
		model.addAttribute("artisti", this.artistaService.findAll());
		return "admin/artisti.html";
	}

	@GetMapping("/deleteArtista/{id}")
	public String deleteArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.findById(id));
		return "admin/deleteArtista.html";
	}

	// METODI GET

	// richiede un singolo Artista tramite id
	@GetMapping("/artista/{id}")
	public String getArtista(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Artista artista = artistaService.findById(id);
		model.addAttribute("artista", artista);
		// ritorna la pagina con i dati dell'entità richiesta
		return "admin/artista.html";
	}

	// richiede un singolo artista tramite id per l'utente semplice
	@GetMapping("/artistaUtente/{id}")
	public String getArtistaUtente(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Artista artista = artistaService.findById(id);
		model.addAttribute("artista", artista);
		// ritorna la pagina con i dati dell'entità richiesta
		return "artista.html";
	}

	// richiede tutti gli Artisti, non c'è id
	@GetMapping("/artisti")
	public String getArtisti(Model model) {
		List<Artista> artisti = artistaService.findAll();
		model.addAttribute("artisti", artisti);
		return "admin/artisti.html";
	}

	// richiede tutti gli artisti per l'utente semplice, non c'è id
	@GetMapping("/artistiUtente")
	public String getArtistiUtente(Model model) {
		List<Artista> artisti = artistaService.findAll();
		model.addAttribute("artisti", artisti);
		return "artisti.html";
	}

	@GetMapping("/artistaForm")
	public String artistaForm(Model model) {
		model.addAttribute("artista", new Artista());
		return "admin/artistaForm.html";
	}

	//richiede tutti le opere dell'artista passato nel path
	@GetMapping("/artista/{id}/opere")
	public String getOpere(@Valid @PathVariable("id") Long id, Model model) {
		Artista artista = artistaService.findById(id);
		model.addAttribute("artista", artista);
		model.addAttribute("opere", artista.getOpere());
		return "admin/opereArtista.html";
	}

	//richiede tutti le opere dell'artista passato nel path per utenti semplici
	@GetMapping("/artistaUtente/{id}/opereUtente")
	public String getOpereUtente(@Valid @PathVariable("id") Long id, Model model) {
		Artista artista = artistaService.findById(id);
		model.addAttribute("artista", artista);
		model.addAttribute("opere", artista.getOpere());
		return "opere.html";
	}
}
