package fr.iut.gestion_equipe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iut.gestion_equipe.entity.Joueur;
import fr.iut.gestion_equipe.repository.EquipeRepository;
import fr.iut.gestion_equipe.repository.JoueurRepository;

@RestController
@RequestMapping("/equipes/{equipeId}/joueurs")
public class JoueurController {

	private final JoueurRepository joueurRepository;
	private final EquipeRepository equipeRepository;

	public JoueurController(JoueurRepository joueurRepository, EquipeRepository equipeRepository) {
		this.joueurRepository = joueurRepository;
		this.equipeRepository = equipeRepository;
	}

	@GetMapping
	public ResponseEntity<List<Joueur>> getAll(@PathVariable("equipeId") Long equipeId) {
		if (!this.equipeRepository.existsById(equipeId)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.joueurRepository.findByEquipeId(equipeId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Joueur> getById(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id) {
		return this.joueurRepository.findByIdAndEquipeId(id, equipeId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Joueur> create(@PathVariable("equipeId") Long equipeId, @RequestBody Joueur joueur) {
		return this.equipeRepository.findById(equipeId).map(equipe -> {
			joueur.setId(null);
			joueur.setEquipe(equipe);
			Joueur saved = this.joueurRepository.save(joueur);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Joueur> update(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id,
			@RequestBody Joueur data) {
		return this.joueurRepository.findByIdAndEquipeId(id, equipeId).map(joueur -> {
			joueur.setNumeroLicence(data.getNumeroLicence());
			joueur.setNom(data.getNom());
			joueur.setPrenom(data.getPrenom());
			joueur.setDateNaissance(data.getDateNaissance());
			joueur.setTaille(data.getTaille());
			joueur.setPoids(data.getPoids());
			joueur.setStatut(data.getStatut());
			return ResponseEntity.ok(this.joueurRepository.save(joueur));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id) {
		Optional<Joueur> joueur = this.joueurRepository.findByIdAndEquipeId(id, equipeId);
		if (joueur.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		this.joueurRepository.delete(joueur.get());
		return ResponseEntity.noContent().build();
	}
}