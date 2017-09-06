package br.com.caelum.ingresso.comparators;

import java.util.Comparator;

import br.com.caelum.ingresso.model.Filme;

public class FilmeComparatorPorDuracao implements Comparator<Filme> {

	@Override
	public int compare(Filme arg0, Filme arg1) {
		return arg0.getDuracao().compareTo(arg1.getDuracao());
	}

}
