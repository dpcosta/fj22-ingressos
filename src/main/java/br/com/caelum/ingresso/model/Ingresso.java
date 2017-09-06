package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ingresso {
	
	@Id
	@GeneratedValue
	private Integer id;
	private BigDecimal preco;
	
	@ManyToOne
	private Sessao sessao;
	
	@ManyToOne
	private Lugar lugar;
	
	private TipoDeIngresso tipoDeIngresso;
	
	
	/***
	 * @deprecated hibernate 
	 */
	public Ingresso(){
		
	}
	
	public Ingresso(Sessao sessao, TipoDeIngresso tipoDeIngresso, Lugar lugar) {
		this.sessao = sessao;
		this.tipoDeIngresso = tipoDeIngresso;
		this.preco = this.tipoDeIngresso.aplicaDesconto(sessao.getPreco());
		this.lugar = lugar;
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
	
	public Lugar getLugar() {
		return lugar;
	}
	
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}
	
	public TipoDeIngresso getTipoDeIngresso() {
		return tipoDeIngresso;
	}
	
	public void setTipoDeIngresso(TipoDeIngresso tipoDeIngresso) {
		this.tipoDeIngresso = tipoDeIngresso;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
