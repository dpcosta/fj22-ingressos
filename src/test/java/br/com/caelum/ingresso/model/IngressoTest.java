package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class IngressoTest {

	private Sessao criaSessaoDeTrintaReais() {
		Filme filme = new Filme("Jogos Mortais", Duration.ofMinutes(120), "Terror", new BigDecimal("10.0"));
		Sala sala = new Sala("3D", new BigDecimal("20"));
		Sessao sessao = new Sessao(LocalTime.of(20, 00), filme, sala);
		return sessao;
	}
	
	@Test
	public void deveCustarPrecoDaSessaoQuandoNaoHaDesconto() {
		
		//arrange
		Sessao sessao = criaSessaoDeTrintaReais();
		
		//act
		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());
		
		//assert
		BigDecimal valorEsperado = new BigDecimal("30.0");
		BigDecimal valorRetornado = ingresso.getPreco();
		
		Assert.assertEquals(valorEsperado, valorRetornado);
		
	}
	
	@Test
	public void deveCustarMetadeDaSessaoQuandoEstudante(){
		
		Sessao sessao = criaSessaoDeTrintaReais();
		
		Ingresso ingresso = new Ingresso(sessao, new DescontoEstudante());
		
		BigDecimal valorEsperado = new BigDecimal("15");
		BigDecimal valorRetornado = ingresso.getPreco();
		
		Assert.assertEquals(valorEsperado, valorRetornado);
	}
	
	@Test
	public void deveCustarTrintaPorCentoQuandoBanco(){
		Sessao sessao = criaSessaoDeTrintaReais();
		
		Ingresso ingresso = new Ingresso(sessao, new DescontoCartaoBanco());
		
		BigDecimal valorEsperado = new BigDecimal("21.00");
		BigDecimal valorRetornado = ingresso.getPreco();
		
		Assert.assertEquals(valorEsperado, valorRetornado);
	}

}
