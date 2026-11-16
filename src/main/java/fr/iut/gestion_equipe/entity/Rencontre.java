package fr.iut.gestion_equipe.entity;

import java.time.LocalDateTime;

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
public class Rencontre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime dateHeure;

	@Column(nullable = false, length = 50)
	private String equipeAdverse;

	@Column(length = 255)
	private String adresse;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Lieu lieu;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Resultat resultat;

	@ManyToOne(optional = false)
	@JoinColumn(name = "equipe_id", nullable = false)
	private Equipe equipe;

	public Rencontre() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateHeure() {
		return this.dateHeure;
	}

	public void setDateHeure(LocalDateTime dateHeure) {
		this.dateHeure = dateHeure;
	}

	public String getEquipeAdverse() {
		return this.equipeAdverse;
	}

	public void setEquipeAdverse(String equipeAdverse) {
		this.equipeAdverse = equipeAdverse;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Lieu getLieu() {
		return this.lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	public Resultat getResultat() {
		return this.resultat;
	}

	public void setResultat(Resultat resultat) {
		this.resultat = resultat;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
}