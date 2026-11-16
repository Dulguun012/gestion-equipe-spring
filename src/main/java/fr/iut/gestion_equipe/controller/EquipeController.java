package fr.iut.gestion_equipe.controller;

import java.util.List;

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

import fr.iut.gestion_equipe.entity.Equipe;
import fr.iut.gestion_equipe.repository.EquipeRepository;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

	private final EquipeRepository equipeRepository;

	public EquipeController(EquipeRepository equipeRepository) {
		this.equipeRepository = equipeRepository;
	}

	@GetMapping
	public List<Equipe> getAll() {
		return this.equipeRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equipe> getById(@PathVariable("id") Long id) {
		return this.equipeRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Equipe> create(@RequestBody Equipe equipe) {
		equipe.setId(null);
		Equipe saved = this.equipeRepository.save(equipe);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable("id") Long id, @RequestBody Equipe data) {
		return this.equipeRepository.findById(id).map(equipe -> {
			equipe.setNom(data.getNom());
			return ResponseEntity.ok(this.equipeRepository.save(equipe));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if (!this.equipeRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.equipeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}