package fr.iut.gestion_equipe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iut.gestion_equipe.dto.FeuilleDeMatchRequest;
import fr.iut.gestion_equipe.dto.ParticipantResponse;
import fr.iut.gestion_equipe.service.FeuilleDeMatchService;

@RestController
@RequestMapping("/equipes/{equipeId}/rencontres/{rencontreId}/feuille")
public class FeuilleDeMatchController {

	private final FeuilleDeMatchService feuilleDeMatchService;

	public FeuilleDeMatchController(FeuilleDeMatchService feuilleDeMatchService) {
		this.feuilleDeMatchService = feuilleDeMatchService;
	}

	// GET -> see the sheet of a match
	@GetMapping
	public List<ParticipantResponse> get(@PathVariable("equipeId") Long equipeId,
			@PathVariable("rencontreId") Long rencontreId) {
		return this.feuilleDeMatchService.getFeuille(equipeId, rencontreId).stream().map(ParticipantResponse::from)
				.toList();
		// For each Participation, convert it to a ParticipantResponse.
	}

	// PUT -> validate and save the sheet (replaces the previous one)
	@PutMapping
	public List<ParticipantResponse> enregistrer(@PathVariable("equipeId") Long equipeId,
			@PathVariable("rencontreId") Long rencontreId, @RequestBody FeuilleDeMatchRequest request) {
		return this.feuilleDeMatchService.enregistrer(equipeId, rencontreId, request).stream()
				.map(ParticipantResponse::from).toList();
	}
}