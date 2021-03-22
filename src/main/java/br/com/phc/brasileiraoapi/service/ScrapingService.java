package br.com.phc.brasileiraoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phc.brasileiraoapi.dto.PartidaGoogleDTO;
import br.com.phc.brasileiraoapi.entity.Partida;
import br.com.phc.brasileiraoapi.util.ScrapingUtil;
import br.com.phc.brasileiraoapi.util.StatusPartida;

import java.util.List;

@Service
public class ScrapingService {

	@Autowired
	private ScrapingUtil scrapingUtil;
	
	@Autowired
	private PartidaService partidaService;
	
	public void verificarPartidaPeriodo() {
		Integer quantidadePartida = partidaService.buscarQuantidadePartidasPeriodo();
		
		if (quantidadePartida > 0) {
			List<Partida> partidas = partidaService.listarPartidasPeriodo();
			
			partidas.forEach(partida -> {
				String urlPartida = scrapingUtil.montaUrlGoogle(partida.getEquipeCasa().getNomeEquipe(),
						partida.getEquipeVisitante().getNomeEquipe());
				
				PartidaGoogleDTO partidaGoogle = scrapingUtil.obtemInformacoesPartida(urlPartida);
				
				if(partidaGoogle.getStatusPartida() != StatusPartida.PARTIDA_NAO_INICIADA) {
					partidaService.atualizaPartida(partida, partidaGoogle);
				}
			});
		}
	}
}
