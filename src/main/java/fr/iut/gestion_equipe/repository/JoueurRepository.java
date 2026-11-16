package fr.iut.gestion_equipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.gestion_equipe.entity.Joueur;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {

	List<Joueur> findByEquipeId(Long equipeId);

	Optional<Joueur> findByIdAndEquipeId(Long id, Long equipeId);
}