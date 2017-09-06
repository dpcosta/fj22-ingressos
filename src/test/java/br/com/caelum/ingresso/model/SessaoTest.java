package br.com.caelum.ingresso.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {

	@Test
	public void DeveTestarOCenario() {
		
		LocalTime duasEMeiaDaTarde = LocalTime.of(14, 30);
		LocalTime umaDaTarde = LocalTime.of(13, 00);
		LocalTime vinteHoras = LocalTime.of(20, 00);
		LocalTime dezManha = LocalTime.of(10, 00);
		LocalTime quinzeParaDezoito = LocalTime.of(17, 45);
		LocalTime quatroDaTarde = LocalTime.of(16, 00);
		
		BigDecimal precoPadrao = new BigDecimal("12.0");
		
		Filme hp1 = new Filme("Harry Potter 1", Duration.ofMinutes(120), "Aventura", precoPadrao);
		Filme hp2 = new Filme("Harry Potter 2", Duration.ofMinutes(120), "Aventura", precoPadrao);
		Filme mmf = new Filme("Meu Malvado Favorito", Duration.ofMinutes(90), "Infantil", precoPadrao);
		Filme nemo = new Filme("Procurando Nemo", Duration.ofMinutes(105), "Infantil", precoPadrao);
		Filme lord = new Filme("Senhor dos Anéis", Duration.ofMinutes(180), "Fantasia", precoPadrao);
		
		Sala sala = new Sala("3D", precoPadrao);
		
		Sessao sessao1 = new Sessao(duasEMeiaDaTarde, hp1, sala);
		
		//sessões já existentes para a sala
		List<Sessao> sessoesExistentes = Arrays.asList(
				new Sessao(umaDaTarde, hp2, sala),
				new Sessao(vinteHoras, nemo, sala),
				new Sessao(dezManha, mmf, sala)
		);
		
		GerenciadorSessao gerenciador = new GerenciadorSessao();
		assertFalse(gerenciador.cabe(sessao1,sessoesExistentes));
		
		
		Sessao sessao2 = new Sessao(quinzeParaDezoito, lord, sala);
		
		assertFalse(gerenciador.cabe(sessao2, sessoesExistentes));
		
		
		Sessao sessao3 = new Sessao(quatroDaTarde, hp2, sala);
		assertTrue(gerenciador.cabe(sessao3, sessoesExistentes));
		
		
	}
	
	@Test
	public void precoDaSessaoDeveSerIgualASomaDoPrecoDaSalaEDoFilme(){
		Filme filme = new Filme("Harry Potter", Duration.ofMinutes(120), "Aventura", new BigDecimal("10.0"));
		Sala sala = new Sala("3D", new BigDecimal("10.0"));
		Sessao sessao = new Sessao(LocalTime.of(14, 00), filme, sala);
		
		BigDecimal valorEsperado = new BigDecimal("20.0");
		BigDecimal valorRetornado = sessao.getPreco();
		
		Assert.assertEquals(valorEsperado, valorRetornado);
	}
	
	@Test
	public void garanteQueOLugarA1EstaOcupadoEOsLugaresA2EA3Disponiveis(){
		Lugar a1 = new Lugar("A",1);
		Lugar a2 = new Lugar("A",2);
		Lugar a3 = new Lugar("A",3);
		
		Filme rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "Aventura", new BigDecimal("13.45"));
		
		Sala bayMarket = new Sala("3D - Bay Market", new BigDecimal("6.55"));
		
		Sessao sessao = new Sessao(LocalTime.now(), rogueOne, bayMarket);
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, a1);
		
		Set<Ingresso> ingressos = Stream.of(ingresso).collect(Collectors.toSet());
		
		sessao.setIngressos(ingressos);
		
		assertFalse(sessao.isDisponivel(a1));
		assertTrue(sessao.isDisponivel(a2));
		assertTrue(sessao.isDisponivel(a3));
		
		
		
		
	}

}
