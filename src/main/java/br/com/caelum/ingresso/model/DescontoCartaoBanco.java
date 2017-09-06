package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

public class DescontoCartaoBanco implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		BigDecimal trintaPorCento = precoOriginal.multiply(new BigDecimal("0.3"));
		return precoOriginal.subtract(trintaPorCento);
	}

}
