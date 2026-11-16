package fr.iut.gestion_equipe.dto;

import fr.iut.gestion_equipe.entity.Poste;

public record RemplacantRequest(Long joueurId, Poste poste) {
}