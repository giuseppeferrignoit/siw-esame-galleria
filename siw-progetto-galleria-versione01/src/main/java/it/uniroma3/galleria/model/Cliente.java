package it.uniroma3.galleria.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "cognome"}))
@NamedQuery (name =  "findAllClients", query = "SELECT c FROM Cliente c")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@NotBlank
	private String nazionalita;
	
	@ManyToMany		
	private List<GalleriaArte> gallerie;
	/*
	@OneToMany(mappedBy="cliente")
	private List<Opera> opere;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Indirizzo indirizzo;

	//---------------------------------------
	/*
	public List<Opera> getOpere() {
		return opere;
	}

	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}
	*/
	public Cliente() {
		this.gallerie = new ArrayList<GalleriaArte>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<GalleriaArte> getGallerie() {
		return gallerie;
	}

	public void setGallerie(List<GalleriaArte> gallerie) {
		this.gallerie = gallerie;
	}
	
}
