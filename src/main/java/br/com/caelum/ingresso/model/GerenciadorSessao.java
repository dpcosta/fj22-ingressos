package br.com.caelum.ingresso.model;

import java.time.LocalTime;
import java.util.List;

public class GerenciadorSessao {

	public boolean cabe(Sessao sessao1, List<Sessao> sessoesExistentes) {

		for (Sessao sessao : sessoesExistentes) {
			
			if (!horarioValido(sessao1, sessao)){
				return false;
			}
		}
		
		
//		Stream<Sessao> stream = sessoesExistentes.filter(s -> horarioValido(sessao1, s));
		
		return true;
	}
	
	private boolean horarioValido(Sessao sessaoNova, Sessao sessaoExistente){
		LocalTime horarioSessao = sessaoNova.getHorario();
		LocalTime horarioSessaoExistente = sessaoExistente.getHorario();
		
		boolean ehAntes = horarioSessao.isBefore(horarioSessaoExistente);
		
		if (ehAntes) {
			return sessaoNova.getHorarioTermino().isBefore(horarioSessaoExistente);
		} else {
			return sessaoExistente.getHorarioTermino().isBefore(horarioSessao);
		}
	}

}
