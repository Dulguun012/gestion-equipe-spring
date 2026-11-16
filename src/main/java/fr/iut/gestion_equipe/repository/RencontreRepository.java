package fr.iut.gestion_equipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.gestion_equipe.entity.Rencontre;

public interface RencontreRepository extends JpaRepository<Rencontre, Long> {

	List<Rencontre> findByEquipeIdOrderByDateHeureAsc(Long equipeId);

	Optional<Rencontre> findByIdAndEquipeId(Long id, Long equipeId);
}