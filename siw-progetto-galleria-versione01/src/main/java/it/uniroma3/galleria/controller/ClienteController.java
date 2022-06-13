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
import it.uniroma3.galleria.service.ClienteService;
import it.uniroma3.galleria.validator.ClienteValidator;


@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

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

			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla pagina di visualizzazione dati inseriti
			return "cliente.html"; 
		}
		else {
			model.addAttribute("cliente", cliente);
			// se ci sono errori si rimanda alla form di inserimento
			return "clienteForm.html"; 
		}
	}


	// METODI PER DELETE

	@GetMapping("/confermaDeleteCliente/{id}")
	public String confermaDeleteCliente(@PathVariable("id") Long id, Model model) {
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
		return "cliente.html";
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
		return "clienti.html";
	}

	@GetMapping("/clienteForm")
	public String artistaForm(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "admin/clienteForm.html";
	}

}
