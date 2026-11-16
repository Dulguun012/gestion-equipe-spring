package fr.iut.gestion_equipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "rencontre_id", "joueur_id" }))
public class Participation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "rencontre_id", nullable = false)
	private Rencontre rencontre;

	@ManyToOne(optional = false)
	@JoinColumn(name = "joueur_id", nullable = false)
	private Joueur joueur;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TitulaireOuRemplacant role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Poste poste;

	public Participation() {
	}

	public Participation(Rencontre rencontre, Joueur joueur, TitulaireOuRemplacant role, Poste poste) {
		this.rencontre = rencontre;
		this.joueur = joueur;
		this.role = role;
		this.poste = poste;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rencontre getRencontre() {
		return this.rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public TitulaireOuRemplacant getRole() {
		return this.role;
	}

	public void setRole(TitulaireOuRemplacant role) {
		this.role = role;
	}

	public Poste getPoste() {
		return this.poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}
}