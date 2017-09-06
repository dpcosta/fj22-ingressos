package br.com.caelum.ingresso.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.comparators.SessaoComparator;
import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.rest.ImdbClient;

@Controller
public class SessaoController {
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@Autowired
	private ImdbClient client;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") int salaId, SessaoForm form){
		ModelAndView modelAndView = new ModelAndView("/sessao/sessao");
		
		form.setSalaId(salaId);
		
		modelAndView.addObject("sala", salaDao.findOne(salaId));
		modelAndView.addObject("filmes", filmeDao.findAll());
		modelAndView.addObject("form", form);
		
		return modelAndView;
	}
	
	
	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result){
		if (result.hasErrors()) return form(form.getSalaId(), form);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
		Sessao sessao = form.toSessao(salaDao, filmeDao);
		sessaoDao.save(sessao);
		return modelAndView;
	}
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId){
		ModelAndView modelAndView = new ModelAndView("sessao/lugares");
		
		
		Sessao sessao = sessaoDao.findOne(sessaoId);
		modelAndView.addObject("sessao", sessao);
		Optional<ImagemCapa> imagemCapa = client.request(sessao.getFilme(), ImagemCapa.class);
		modelAndView.addObject("imagemCapa", imagemCapa .orElse(new ImagemCapa()));
		modelAndView.addObject("tiposDeIngressos", TipoDeIngresso.values());
		
		return modelAndView;
	}
	
	public static void main(String[] args) {
		
		LocalTime duasEMeiaDaTarde = LocalTime.of(14, 30);
		LocalTime umaDaTarde = LocalTime.of(13, 00);
		LocalTime vinteHoras = LocalTime.of(20, 00);
		LocalTime dezManha = LocalTime.of(10, 00);
		
		BigDecimal precoPadrao = new BigDecimal("12.0");
		
		
		Filme hp1 = new Filme("Harry Potter 1", Duration.ofMinutes(120), "Aventura", precoPadrao);
		Filme hp2 = new Filme("Harry Potter 2", Duration.ofMinutes(120), "Aventura", precoPadrao);
		Filme mmf = new Filme("Meu Malvado Favorito", Duration.ofMinutes(90), "Infantil", precoPadrao);
		Filme nemo = new Filme("Procurando Nemo", Duration.ofMinutes(105), "Infantil", precoPadrao);
		
		Sala sala = new Sala("3D", precoPadrao);
		
		Sessao sessao1 = new Sessao(duasEMeiaDaTarde, hp1, sala);
		
		//sessões já existentes para a sala
		List<Sessao> sessoesExistentes = Arrays.asList(
				new Sessao(umaDaTarde, hp2, sala),
				new Sessao(vinteHoras, nemo, sala),
				new Sessao(dezManha, mmf, sala)
		);
		
		//ordenar as sessões...
		sessoesExistentes.sort(new SessaoComparator());
		
		//para percorrer a lista, costumamos usar...
		for(Sessao s : sessoesExistentes){
			System.out.println(s.getFilme().getNome());
		}
		
		//ou usando expressões lambda...
		sessoesExistentes.sort( (s1, s2) -> s1.getHorario().compareTo(s2.getHorario()) );
		
		//ou ainda...
		sessoesExistentes.sort(Comparator.comparing(s -> s.getHorario()));
		
		//quer usar mais de uma vez? declare a função de comparação como variável
		Function<Sessao, LocalTime> funcaoDeComparacao = s -> s.getHorario();
		
		sessoesExistentes.sort(Comparator.comparing(funcaoDeComparacao));
		
		//FILTER: e se eu quisesse FILTRAR as sessões que acontecem apenas de manhã e imprimir no console?
		
		List<Sessao> sessoesDaManha = sessoesExistentes.stream()
				.filter(s -> s.getHorario().isBefore(LocalTime.of(11, 59)))
				.collect(Collectors.toList());
		
		sessoesDaManha.forEach(s -> System.out.println(s.getFilme().getNome()));
		
		//ou usando uma função que recebe
		
		//MAP: também posso tranformar a lista de um tipo em uma lista de outro tipo! 
		List<Integer> idsDosFilmesDasSessoes = sessoesExistentes.stream()
				.map(s -> s.getFilme().getId())
				.collect(Collectors.toList());
		
		idsDosFilmesDasSessoes.forEach(System.out::println);
		
		
		//REDUCE: também posso reduzir a lista em um valor único
		Optional<Long> totalDuracao = sessoesExistentes.stream()
				.map(s -> s.getFilme().getDuracao().toMinutes())
				.reduce(Math::addExact);
		
		if (totalDuracao.isPresent()) System.out.println(totalDuracao.get());
		
		
		//como testar?
		
	}
	
	
}


