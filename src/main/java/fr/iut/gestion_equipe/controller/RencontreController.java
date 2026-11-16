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

import fr.iut.gestion_equipe.entity.Rencontre;
import fr.iut.gestion_equipe.repository.EquipeRepository;
import fr.iut.gestion_equipe.repository.RencontreRepository;

@RestController
@RequestMapping("/equipes/{equipeId}/rencontres")
public class RencontreController {

	private final RencontreRepository rencontreRepository;
	private final EquipeRepository equipeRepository;

	public RencontreController(RencontreRepository rencontreRepository, EquipeRepository equipeRepository) {
		this.rencontreRepository = rencontreRepository;
		this.equipeRepository = equipeRepository;
	}

	@GetMapping
	public ResponseEntity<List<Rencontre>> getAll(@PathVariable("equipeId") Long equipeId) {
		if (!this.equipeRepository.existsById(equipeId)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.rencontreRepository.findByEquipeIdOrderByDateHeureAsc(equipeId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Rencontre> getById(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id) {
		return this.rencontreRepository.findByIdAndEquipeId(id, equipeId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Rencontre> create(@PathVariable("equipeId") Long equipeId, @RequestBody Rencontre rencontre) {
		return this.equipeRepository.findById(equipeId).map(equipe -> {
			rencontre.setId(null); // force a creation
			rencontre.setEquipe(equipe); // the team comes from the URL
			return ResponseEntity.status(HttpStatus.CREATED).body(this.rencontreRepository.save(rencontre));
		}).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Rencontre> update(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id,
			@RequestBody Rencontre data) {
		return this.rencontreRepository.findByIdAndEquipeId(id, equipeId).map(rencontre -> {
			rencontre.setDateHeure(data.getDateHeure());
			rencontre.setEquipeAdverse(data.getEquipeAdverse());
			rencontre.setAdresse(data.getAdresse());
			rencontre.setLieu(data.getLieu());
			rencontre.setResultat(data.getResultat());
			return ResponseEntity.ok(this.rencontreRepository.save(rencontre));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("equipeId") Long equipeId, @PathVariable("id") Long id) {
		Optional<Rencontre> rencontre = this.rencontreRepository.findByIdAndEquipeId(id, equipeId);
		if (rencontre.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		this.rencontreRepository.delete(rencontre.get());
		return ResponseEntity.noContent().build();
	}
}