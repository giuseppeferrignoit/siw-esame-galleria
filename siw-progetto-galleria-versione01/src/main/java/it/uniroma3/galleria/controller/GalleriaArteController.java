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

import it.uniroma3.galleria.model.GalleriaArte;
import it.uniroma3.galleria.model.Opera;
import it.uniroma3.galleria.service.ClienteService;
import it.uniroma3.galleria.service.GalleriaArteService;
import it.uniroma3.galleria.service.OperaService;
import it.uniroma3.galleria.validator.GalleriaArteValidator;

@Controller
public class GalleriaArteController {

	@Autowired
	private GalleriaArteService galleriaService;

	@Autowired
	private GalleriaArteValidator galleriaValidator;

	@Autowired
	private OperaService operaService;

	@Autowired
	private ClienteService clienteService;


	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	 */

	// METODO POST PER INSERIRE UN NUOVO BUFFET

	@PostMapping("/galleria")
	public String addArtista(@Valid @ModelAttribute(value="artista") GalleriaArte galleria, 
			BindingResult bindingResult, Model model) {

		/* Se non ci sono errori inserisce la ricorrenza di Galleria 
		 * tramite la save del service 
		 * */

		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.galleriaValidator.validate(galleria, bindingResult);

		if (!bindingResult.hasErrors()) {

			this.galleriaService.save(galleria); // salvo un oggetto GalleriaArte
			model.addAttribute("galleria", galleria);

			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla pagina di visualizzazione dati inseriti
			return "admin/galleria.html"; 
		}
		else {
			model.addAttribute("galleria", galleria);
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/galleriaForm.html"; 
		}
	}

	// METODI PER LE OPERE DELLA GALLERIA

	//associa l'opera il cui id è passato nel path alla Galleriia
	@GetMapping("/galleria/{idGalleria}/{idOpera}")
	public String addOpera(@PathVariable("idGalleria") Long idGalleria,
			@PathVariable("idOpera") Long idOpera, Model model) {
		GalleriaArte galleria = this.galleriaService.findById(idGalleria);
		Opera opera = this.operaService.findById(idOpera);
		this.galleriaService.addOpera(galleria, opera);
		model.addAttribute("galleria", galleria);
		model.addAttribute("opereAssenti", this.galleriaService.findOpereNotInGalleria(galleria));
		return "admin/galleria.html";

	}

	//ritorna le opere disponibili per l'associazione
	@GetMapping("/galleria/{id}/scegliNuovaOpera")
	public String scegliOperaDaAssociare(@PathVariable("id") Long id, Model model) {
		GalleriaArte galleria = this.galleriaService.findById(id);
		model.addAttribute("galleria", galleria);
		model.addAttribute("opere", operaService.findOpereNonAssociate());
		return "admin/opereNonAssociate.html";
	}

	//si associa una nuova opera alla galleria il cui id è passato nel path
	@GetMapping("/associaOpera/{idOpera}/{idGalleria}")
	public String associaOpera(@PathVariable("idOpera") Long idOpera, 
			@PathVariable("idGalleria") Long idGalleria,Model model) {
		GalleriaArte galleria = this.galleriaService.findById(idGalleria);
		Opera opera = this.operaService.findById(idOpera);
		model.addAttribute("galleria", galleria);
		model.addAttribute("opera", opera);
		return "admin/associaOperaGalleria.html";
	}
	
	@GetMapping("/confermaAssociazione/{idOpera}/{idGalleria}")
	public String confermaAssociaOpera(@PathVariable("idOpera") Long idOpera, 
			@PathVariable("idGalleria") Long idGalleria,Model model) {
		GalleriaArte galleria = this.galleriaService.findById(idGalleria);
		Opera opera = this.operaService.findById(idOpera);
		this.galleriaService.addOpera(galleria, opera);
		this.operaService.setGalleria(galleria, opera);
		model.addAttribute("galleria", galleria);
		return "admin/galleria.html";
	}
	
	//si associa una nuova opera alla galleria il cui id è passato nel path
		@GetMapping("/dissociaOpera/{idOpera}")
		public String dissociaOpera(@PathVariable("idOpera") Long idOpera, Model model) {			
			Opera opera = this.operaService.findById(idOpera);
			GalleriaArte galleria = this.galleriaService.findById(opera.getGallery().getId());
			model.addAttribute("galleria", galleria);
			model.addAttribute("opera", opera);
			return "admin/dissociaOperaGalleria.html";
		}
		
		@GetMapping("/confermaDissociazioneOpera/{idOpera}")
		public String confermaDisassociazioneOperaOpera(@PathVariable("idOpera") Long idOpera, Model model) {
			Opera opera = this.operaService.findById(idOpera);
			GalleriaArte galleria = this.galleriaService.findById(opera.getGallery().getId());
			this.galleriaService.removeOpera(galleria, opera);
			this.operaService.setGalleriaNull(galleria, opera);
			model.addAttribute("galleria", galleria);
			model.addAttribute("opera", opera);
			return "admin/galleria.html";
		}
	
	// METODI PER DELETE

	@GetMapping("/confermaDeleteGalleria/{id}")
	public String confermaDeleteGalleria(@PathVariable("id") Long id, Model model) {
		this.operaService.removeGalleriaFromOpere(this.galleriaService.findById(id));
		this.galleriaService.deleteById(id);
		model.addAttribute("gallerie", this.galleriaService.findAll());
		return "admin/gallerie.html";
	}

	@GetMapping("/deleteGalleria/{id}")
	public String deleteGalleria(@PathVariable("id") Long id, Model model) {
		model.addAttribute("galleria", this.galleriaService.findById(id));
		return "admin/deleteGalleria.html";
	}

	// METODI GET

	// richiede ua singola Galleria tramite id
	@GetMapping("/galleria/{id}")
	public String getGalleria(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		GalleriaArte galleria = galleriaService.findById(id);
		model.addAttribute("galleria", galleria);
		// ritorna la pagina con i dati dell'entità richiesta
		return "admin/galleria.html";
	}

	// richiede una singola galleria tramite id per l'utente semplice
	@GetMapping("/galleriaUtente/{id}")
	public String getGalleriaUtente(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		GalleriaArte galleria = galleriaService.findById(id);
		model.addAttribute("galleria", galleria);
		// ritorna la pagina con i dati dell'entità richiesta
		return "galleria.html";
	}

	// richiede tutte le Gallerie, non c'è id
	@GetMapping("/gallerie")
	public String getGalleria(Model model) {
		List<GalleriaArte> gallerie = galleriaService.findAll();
		model.addAttribute("gallerie", gallerie);
		return "admin/gallerie.html";
	}

	// richiede tutti gli artisti per l'utente semplice, non c'è id
	@GetMapping("/gallerieUtente")
	public String getGallerieUtente(Model model) {
		List<GalleriaArte> gallerie = galleriaService.findAll();
		model.addAttribute("gallerie", gallerie);
		return "gallerie.html";
	}

	@GetMapping("/galleriaForm")
	public String galleriaForm(Model model) {
		model.addAttribute("galleria", new GalleriaArte());
		return "admin/galleriaForm.html";
	}

	//richiede tutte le opere della galleria passata nel path
	@GetMapping("/galleria/{id}/opere")
	public String getOpereGalleria(@Valid @PathVariable("id") Long id, Model model) {
		GalleriaArte galleria = galleriaService.findById(id);
		model.addAttribute("opere", galleria.getOpere());
		model.addAttribute("galleria", galleria);
		return "admin/opereGalleria.html";
	}

	//richiede tutte le opere della galleria  passata nel path per utenti semplici
	@GetMapping("/galleriaUtente/{id}/opereUtente")
	public String getOpereGalleriaUtente(@Valid @PathVariable("id") Long id, Model model) {
		GalleriaArte galleria = galleriaService.findById(id);
		model.addAttribute("opere", galleria.getOpere());
		model.addAttribute("galleria", galleria);
		return "opere.html";
	}
}

