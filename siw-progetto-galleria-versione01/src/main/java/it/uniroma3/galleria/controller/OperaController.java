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

import it.uniroma3.galleria.model.Opera;
import it.uniroma3.galleria.service.ArtistaService;
import it.uniroma3.galleria.service.ClienteService;
import it.uniroma3.galleria.service.OperaService;
import it.uniroma3.galleria.validator.OperaValidator;


@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;

	@Autowired
	private ArtistaService artistaService;

	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	 */

	// METODO POST PER INSERIRE UN NUOVO BUFFET

	@PostMapping("/artista/{idArtista}/opera")
	public String addOpera(@Valid @ModelAttribute(value="opera") Opera opera, @PathVariable("idArtista") Long idArtista,
			BindingResult bindingResult, Model model) {

		// Creo una entità Opera dai dati di input della stringa HTTP usando i metodi Get
		// I risultati della validazione sono riportati in BindingResult
		/* 
		 * Se non ci sono errori inserisce la ricorrenza di Opera 
		 * tramite la save del suo service 
		 * */

		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.operaValidator.validate(opera, bindingResult);

		if (!bindingResult.hasErrors()) { 

			// Se non ci sono errori
			this.operaService.save(opera, artistaService.findById(idArtista)); // Salvo un oggetto Opera
			model.addAttribute("opera", opera);
			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla form di visualizzazione dati inseriti
			return "admin/opera.html"; 

		}
		else {
			// Se ci sono errori si va alla form di inserimento opera
			model.addAttribute("opera", opera);
			return "admin/operaForm.html"; 
		}
	} 

	// METODI PER DELETE

	@GetMapping("/confermaDeleteOpera/{id}")
	public String confermaDeleteOpera(@PathVariable("id") Long id, Model model) {
		this.operaService.deleteById(id);
		model.addAttribute("opere", this.operaService.findAll());
		return "admin/opere.html";
	}

	@GetMapping("/deleteOpera/{id}")
	public String deleteOpera(@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", this.operaService.findById(id));
		return "admin/deleteOpera.html";
	}

	// METODI GET

	// richiede una singola opera tramite id
	@GetMapping("/opera/{id}")
	public String getOpera(@PathVariable("id") Long id, Model model) { // id è una variabile associata al path
		Opera opera = operaService.findById(id);
		model.addAttribute("opera", opera);
		return "admin/opera.html"; // ritorna la pagina con i dati dell'entità richiesta
	}
	
	// richiede una singola opera tramite id per l'utente generico
	@GetMapping("/operaUtente/{id}")
	public String getOperaUtente(@PathVariable("id") Long id, Model model) { // id è una variabile associata al path
		Opera opera = operaService.findById(id);
		model.addAttribute("opera", opera);
		return "opera.html"; // ritorna la pagina con i dati dell'entità richiesta
	}

	// richiede tutte le opere, non c'è id
	@GetMapping("/opere")
	public String getOpere(Model model) {
		List<Opera> opere = operaService.findAll();
		model.addAttribute("opere", opere);
		return "admin/opere.html";
	}

	// richiede tutte le opere per l'utente semplice, non c'è id
	@GetMapping("/opereUtente")
	public String getOpereUtente(Model model) {
		List<Opera> opere = operaService.findAll();
		model.addAttribute("opere", opere);
		return "opere.html";
	}

	// crea una nuova opera associata all'artista passato nel path
	@GetMapping("/artista/{idArtista}/nuovaOpera")
	public String createOpera(@PathVariable("idArtista") Long idArtista, Model model) {
		Opera opera = new Opera();
		model.addAttribute("artista", artistaService.findById(idArtista));
		model.addAttribute("opera", opera);
		return "admin/operaForm.html";
	}

	//crea una nuova opera
	@GetMapping("/newOpera")
	public String createNewOpera(Model model) {
		model.addAttribute("artisti", artistaService.findAll());
		return "admin/newOpera.html";
	}

}
