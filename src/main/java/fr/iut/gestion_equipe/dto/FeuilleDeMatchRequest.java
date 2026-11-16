package fr.iut.gestion_equipe.dto;

import java.util.List;
import java.util.Map;

import fr.iut.gestion_equipe.entity.Poste;

public record FeuilleDeMatchRequest(Map<Poste, Long> titulaires, List<RemplacantRequest> remplacants) {
}