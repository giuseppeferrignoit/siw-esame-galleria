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

import it.uniroma3.galleria.model.Cliente;
import it.uniroma3.galleria.model.Indirizzo;
import it.uniroma3.galleria.service.ClienteService;
import it.uniroma3.galleria.service.IndirizzoService;
import it.uniroma3.galleria.service.OperaService;
import it.uniroma3.galleria.validator.ClienteValidator;


@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private OperaService operaService;
	
	@Autowired
	private IndirizzoService indirizzoService;
	
	@Autowired
	private ClienteValidator clienteValidator;

	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	 */

	// METODO POST PER INSERIRE UN NUOVO CLIENTE

	@PostMapping("/cliente")
	public String addCliente(@Valid @ModelAttribute(value="cliente") Cliente cliente, 
			BindingResult bindingResult, Model model) {

		/* Se non ci sono errori inserisce la ricorrenza di Cliente 
		 * tramite la save del service 
		 * */

		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.clienteValidator.validate(cliente, bindingResult);

		if (!bindingResult.hasErrors()) {

			this.clienteService.save(cliente); // salvo un oggetto cliente
			model.addAttribute("cliente", cliente);
			model.addAttribute("indirizzo", indirizzoService.creaIndirizzo());
			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla pagina di visualizzazione dati inseriti
			return "admin/indirizzoForm.html"; 
		}
		else {
			model.addAttribute("cliente", cliente);
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/clienteForm.html"; 
		}
	}


	// METODI PER DELETE

	@GetMapping("/confermaDeleteCliente/{id}")
	public String confermaDeleteCliente(@PathVariable("id") Long id, Model model) {
		this.operaService.removeClienteFromOpere(this.clienteService.findById(id));
		this.clienteService.deleteById(id);
		model.addAttribute("clienti", this.clienteService.findAll());
		return "admin/clienti.html";
	}

	@GetMapping("/deleteCliente/{id}")
	public String deleteCliente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cliente", this.clienteService.findById(id));
		return "admin/deleteCliente.html";
	}

	// METODI GET

	
	// richiede un singolo cliente tramite id
	@GetMapping("/cliente/{id}")
	public String getCliente(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Cliente cliente = clienteService.findById(id);
		model.addAttribute("cliente", cliente);
		// ritorna la pagina con i dati dell'entità richiesta
		return "admin/cliente.html";
	}

	// richiede tutti i Clienti, non c'è id
	@GetMapping("/clienti")
	public String getClienti(Model model) {
		List<Cliente> clienti = clienteService.findAll();
		model.addAttribute("clienti", clienti);
		return "admin/clienti.html";
	}

	// richiede tutti i Clienti per l'utente semplice, non c'è id
	@GetMapping("/clientiUtente")
	public String getClientiUtente(Model model) {
		List<Cliente> clienti = clienteService.findAll();
		model.addAttribute("clienti", clienti);
		return "admin/clienti.html";
	}

	@GetMapping("/clienteForm")
	public String artistaForm(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "admin/clienteForm.html";
	}
	
	@GetMapping("/cliente/{id}/opere")
	public String opereAcquistate(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cliente", clienteService.findById(id));
		model.addAttribute("opere", clienteService.getOpereAcquistate(id));
		return "admin/opereCliente.html";
	}
	
	@GetMapping("/cliente/{id}/scegliOperaDaAcquistare")
	public String getOpereNonAcquistate(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cliente", clienteService.findById(id));
		model.addAttribute("opere", operaService.findOpereNonAcquistate());
		return "admin/acquistaOpera.html";
	}
	
	@GetMapping("/cliente/{idCliente}/opera/{idOpera}")
	public String acquistaOpera(@PathVariable("idCliente") Long idCliente,
			@PathVariable("idOpera") Long idOpera, Model model) {
		model.addAttribute("cliente", clienteService.findById(idCliente));
		model.addAttribute("opera", operaService.findById(idOpera));
		return "admin/confermaAcquistoOpera.html";
	}
	
	@GetMapping("/cliente/{idCliente}/confermaOpera/{idOpera}")
	public String confermaAcquistoOpera(@PathVariable("idCliente") Long idCliente, 
			@PathVariable("idOpera") Long idOpera, Model model) {
		clienteService.acquistaOpera(idCliente, idOpera);
		model.addAttribute("cliente", clienteService.findById(idCliente));
		return "admin/cliente.html";
	}
	
}
