package fr.iut.gestion_equipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.gestion_equipe.entity.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

	List<Participation> findByRencontreId(Long rencontreId);

	boolean existsByJoueurId(Long joueurId);
}