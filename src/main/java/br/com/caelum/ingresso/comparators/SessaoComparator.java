package br.com.caelum.ingresso.comparators;

import java.util.Comparator;

import br.com.caelum.ingresso.model.Sessao;

public class SessaoComparator implements Comparator<Sessao> {

	@Override
	public int compare(Sessao sessao1, Sessao sessao2) {
		return sessao1.getHorario().compareTo(sessao2.getHorario());
	}

}
