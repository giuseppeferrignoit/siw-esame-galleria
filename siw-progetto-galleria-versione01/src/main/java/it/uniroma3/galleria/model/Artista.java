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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "cognome"}))
@NamedQuery (name =  "findAllArtists", query = "SELECT a FROM Artista a")
public class Artista {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@NotBlank
	private String nazionalita;
	
	@OneToMany(mappedBy="artista", cascade = CascadeType.ALL)
	private List<Opera> opere;
	
	//-------------------------------
	
	public Artista() {
		this.opere = new ArrayList<Opera>();
	}
	
	public List<Opera> getOpere() {
		return opere;
	}

	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}
	
	public void addOpera(Opera opera) {
		this.getOpere().add(opera);
		opera.setArtista(this);
	}
	
	public void removeOpera(Opera opera) {
		this.getOpere().remove(opera);
		opera.setArtista(null);
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
	
	@Override
	public int hashCode() {
		return this.nome.hashCode() + this.cognome.hashCode()
		     + this.nazionalita.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != Artista.class)
			return false;
		Artista that = (Artista) obj;
		return this.nome.equals(that.getNome()) &&
		       this.cognome.equals(that.getCognome()) &&
		       this.nazionalita.equals(that.getNazionalita());
	}
	
	@Override
	public String toString() {
		return this.nome + " " + this.cognome;
	}
	
}