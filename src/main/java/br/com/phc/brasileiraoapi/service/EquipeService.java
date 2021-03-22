package br.com.phc.brasileiraoapi.service;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.brasileiraoapi.entity.Equipe;
import br.com.phc.brasileiraoapi.repository.EquipeRepository;
import br.com.phc.brasileiraoapi.exception.BadRequestException;
import br.com.phc.brasileiraoapi.exception.NotFoundException;
import br.com.phc.brasileiraoapi.dto.EquipeDTO;
import br.com.phc.brasileiraoapi.dto.EquipeResponseDTO;

@Service
public class EquipeService {
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Equipe bucarEquipeId(Long id) {
		
		return equipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o id informado: "+ id));
	}
	
	public Equipe buscarEquipePorNome(String nomeEquipe) {
		return equipeRepository.findByNomeEquipe(nomeEquipe)
				.orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o nome informado: " + nomeEquipe));
	}
	
	public EquipeResponseDTO listarEquipes() {
		EquipeResponseDTO equipes = new EquipeResponseDTO();
		equipes.setEquipes(equipeRepository.findAll());
		return equipes;
	}
	public Equipe inserirEquipe(@Valid EquipeDTO dto) {
		boolean exists = equipeRepository.existsByNomeEquipe(dto.getNomeEquipe());
		if(exists) {
			throw new BadRequestException("Já existe uma equipe cadastrada com este nome informado. ");
		}
		Equipe equipe = modelMapper.map(dto, Equipe.class);
		return equipeRepository.save(equipe);
	}
	public void alterarEquipe(Long id, @Valid EquipeDTO dto) {
		boolean exists = equipeRepository.existsById(id);
		if(!exists) {
			throw new BadRequestException("Não foi possível alterar a equipe: ID inexistente");
		}
		Equipe equipe = modelMapper.map(dto, Equipe.class);
		equipe.setId(id);
		equipeRepository.save(equipe);
	}
}
