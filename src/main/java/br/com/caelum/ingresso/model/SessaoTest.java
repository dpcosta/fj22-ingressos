package br.com.caelum.ingresso.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

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
		
		Filme hp1 = new Filme("Harry Potter 1", Duration.ofMinutes(120), "Aventura");
		Filme hp2 = new Filme("Harry Potter 2", Duration.ofMinutes(120), "Aventura");
		Filme mmf = new Filme("Meu Malvado Favorito", Duration.ofMinutes(90), "Infantil");
		Filme nemo = new Filme("Procurando Nemo", Duration.ofMinutes(105), "Infantil");
		Filme lord = new Filme("Senhor dos Anéis", Duration.ofMinutes(180), "Fantasia");
		
		Sala sala = new Sala("3D");
		
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

}
