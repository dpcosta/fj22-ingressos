package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

public class Ingresso {
	
	private BigDecimal preco;
	private Sessao sessao;
	
	public Ingresso(Sessao sessao, Desconto desconto) {
		this.sessao = sessao;
		this.preco = desconto.aplicarDescontoSobre(sessao.getPreco());
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public Sessao getSessao() {
		return sessao;
	}
	
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	
}
