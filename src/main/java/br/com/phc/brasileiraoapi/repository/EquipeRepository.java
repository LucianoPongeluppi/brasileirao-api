package br.com.phc.brasileiraoapi.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.phc.brasileiraoapi.entity.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{
	public Optional<Equipe> findByNomeEquipe(String nomeEquipe);
	
	public boolean existsByNomeEquipe(String nomeEquipe);
}
