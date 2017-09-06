package br.com.caelum.ingresso.model;

import java.util.Comparator;

public class SessaoComparator implements Comparator<Sessao> {

	@Override
	public int compare(Sessao sessao1, Sessao sessao2) {
		return sessao1.getHorario().compareTo(sessao2.getHorario());
	}

}
