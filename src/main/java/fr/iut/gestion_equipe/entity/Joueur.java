package fr.iut.gestion_equipe.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Joueur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String numeroLicence;
	// License number: required, and two players can't share one.

	@Column(nullable = false, length = 50)
	private String nom;

	@Column(nullable = false, length = 50)
	private String prenom;

	private LocalDate dateNaissance;

	private Double taille;
	private Double poids;

	@Enumerated(EnumType.STRING)

	@Column(nullable = false, length = 20)
	private JoueurStatut statut = JoueurStatut.ACTIF;

	@ManyToOne(optional = false)
	@JoinColumn(name = "equipe_id", nullable = false)
	private Equipe equipe;

	public Joueur() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroLicence() {
		return this.numeroLicence;
	}

	public void setNumeroLicence(String numeroLicence) {
		this.numeroLicence = numeroLicence;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Double getTaille() {
		return this.taille;
	}

	public void setTaille(Double taille) {
		this.taille = taille;
	}

	public Double getPoids() {
		return this.poids;
	}

	public void setPoids(Double poids) {
		this.poids = poids;
	}

	public JoueurStatut getStatut() {
		return this.statut;
	}

	public void setStatut(JoueurStatut statut) {
		this.statut = statut;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
}