package it.uniroma3.galleria.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@OneToMany(mappedBy="cliente", cascade=CascadeType.MERGE)
	private List<Opera> opere;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Indirizzo indirizzo;

	//---------------------------------------

	public Cliente() {
		this.opere = new ArrayList<Opera>();
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

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	@Override
	public int hashCode() {
		return this.getNome().hashCode() + this.getCognome().hashCode();
	}
	
	public List<Opera> getOpere() {
		return opere;
	}

	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}
	
	public void addOpera(Opera opera) {
		this.getOpere().add(opera);
	}
	
	public void removeOpera(Opera opera) {
		this.getOpere().remove(opera);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != Cliente.class)
			return false;
		Cliente that = (Cliente) obj;
		return this.nome.equals(that.getNome()) && 
			   this.cognome.equals(that.getCognome());
	}
	
	@Override
	public String toString() {
		return this.nome + " " + this.cognome;
	}
	
}
