package fr.iut.gestion_equipe.dto;

import fr.iut.gestion_equipe.entity.Participation;
import fr.iut.gestion_equipe.entity.Poste;
import fr.iut.gestion_equipe.entity.TitulaireOuRemplacant;

public record ParticipantResponse(Long joueurId, String nom, String prenom, TitulaireOuRemplacant role, Poste poste) {

	public static ParticipantResponse from(Participation p) {
		return new ParticipantResponse(p.getJoueur().getId(), p.getJoueur().getNom(), p.getJoueur().getPrenom(),
				p.getRole(), p.getPoste());
	}
}