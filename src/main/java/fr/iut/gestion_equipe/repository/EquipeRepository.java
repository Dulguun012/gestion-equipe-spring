package fr.iut.gestion_equipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.iut.gestion_equipe.entity.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}