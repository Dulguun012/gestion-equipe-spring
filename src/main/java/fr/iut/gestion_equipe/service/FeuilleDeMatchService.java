package fr.iut.gestion_equipe.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fr.iut.gestion_equipe.dto.FeuilleDeMatchRequest;
import fr.iut.gestion_equipe.dto.RemplacantRequest;
import fr.iut.gestion_equipe.entity.Joueur;
import fr.iut.gestion_equipe.entity.JoueurStatut;
import fr.iut.gestion_equipe.entity.Participation;
import fr.iut.gestion_equipe.entity.Poste;
import fr.iut.gestion_equipe.entity.Rencontre;
import fr.iut.gestion_equipe.entity.TitulaireOuRemplacant;
import fr.iut.gestion_equipe.repository.JoueurRepository;
import fr.iut.gestion_equipe.repository.ParticipationRepository;
import fr.iut.gestion_equipe.repository.RencontreRepository;

@Service
// "This class holds business logic." Spring creates it and injects it, like the repositories.
public class FeuilleDeMatchService {

	private final RencontreRepository rencontreRepository;
	private final JoueurRepository joueurRepository;
	private final ParticipationRepository participationRepository;

	public FeuilleDeMatchService(RencontreRepository rencontreRepository, JoueurRepository joueurRepository,
			ParticipationRepository participationRepository) {
		this.rencontreRepository = rencontreRepository;
		this.joueurRepository = joueurRepository;
		this.participationRepository = participationRepository;
	}

	// ---------- Read the sheet of a match ----------
	public List<Participation> getFeuille(Long equipeId, Long rencontreId) {
		Rencontre rencontre = this.trouverRencontre(equipeId, rencontreId);
		return this.participationRepository.findByRencontreId(rencontre.getId());
	}

	// ---------- Check and save a sheet ----------
	@Transactional
	// All or nothing: if anything fails, NOTHING is changed in the database.
	public List<Participation> enregistrer(Long equipeId, Long rencontreId, FeuilleDeMatchRequest request) {
		Rencontre rencontre = this.trouverRencontre(equipeId, rencontreId);

		// If a part is missing in the JSON, treat it as empty instead of crashing.
		Map<Poste, Long> titulaires = request.titulaires() == null ? Map.of() : request.titulaires();
		List<RemplacantRequest> remplacants = request.remplacants() == null ? List.of() : request.remplacants();

		// RULE 1 (minimum of players): every position must have a starter.
		for (Poste poste : Poste.values()) {
			if (titulaires.get(poste) == null) {
				throw this.erreur("Feuille incomplète : il manque un titulaire au poste " + poste);
			}
		}

		List<Participation> participations = new ArrayList<>();
		Set<Long> dejaSelectionnes = new HashSet<>();
		// A Set remembers who we've already added, to catch duplicates.

		// Starters
		for (Map.Entry<Poste, Long> entree : titulaires.entrySet()) {
			Joueur joueur = this.verifierJoueur(equipeId, entree.getValue(), dejaSelectionnes);
			participations.add(new Participation(rencontre, joueur, TitulaireOuRemplacant.TITULAIRE, entree.getKey()));
		}

		// Substitutes
		for (RemplacantRequest remplacant : remplacants) {
			if (remplacant.poste() == null) {
				throw this.erreur("Chaque remplaçant doit avoir un poste");
			}
			Joueur joueur = this.verifierJoueur(equipeId, remplacant.joueurId(), dejaSelectionnes);
			participations
					.add(new Participation(rencontre, joueur, TitulaireOuRemplacant.REMPLACANT, remplacant.poste()));
		}

		// Everything is valid: replace the old sheet with the new one.
		this.participationRepository.deleteAll(this.participationRepository.findByRencontreId(rencontre.getId()));
		this.participationRepository.flush();
		// flush = "run the DELETE right now", before the INSERTs,
		// otherwise re-selecting the same player would hit the unique constraint.
		return this.participationRepository.saveAll(participations);
	}

	// RULES 2, 3, 4 for each selected player
	private Joueur verifierJoueur(Long equipeId, Long joueurId, Set<Long> dejaSelectionnes) {
		if (joueurId == null) {
			throw this.erreur("Identifiant de joueur manquant");
		}
		// RULE 2: the player exists AND belongs to this team
		Joueur joueur = this.joueurRepository.findByIdAndEquipeId(joueurId, equipeId)
				.orElseThrow(() -> this.erreur("Le joueur " + joueurId + " n'existe pas dans cette équipe"));

		// RULE 3: only active players
		if (joueur.getStatut() != JoueurStatut.ACTIF) {
			throw this.erreur("Le joueur " + joueur.getPrenom() + " n'est pas actif (" + joueur.getStatut() + ")");
		}
		// RULE 4: not selected twice (add() returns false if already in the Set)
		if (!dejaSelectionnes.add(joueurId)) {
			throw this.erreur("Le joueur " + joueur.getPrenom() + " est sélectionné deux fois");
		}
		return joueur;
	}

	private Rencontre trouverRencontre(Long equipeId, Long rencontreId) {
		return this.rencontreRepository.findByIdAndEquipeId(rencontreId, equipeId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rencontre introuvable"));
	}

	private ResponseStatusException erreur(String message) {
		return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		// Throwing this stops everything and makes Spring answer 400 + the message.
	}
}