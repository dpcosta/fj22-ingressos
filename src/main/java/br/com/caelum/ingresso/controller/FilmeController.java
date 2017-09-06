package br.com.caelum.ingresso.controller;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.comparators.FilmeComparatorPorDuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by nando on 03/03/17.
 */
@Controller
public class FilmeController {


    @Autowired
    private FilmeDao filmeDao;

    @Autowired
    private SessaoDao sessaoDao;

    @GetMapping({"/admin/filme", "/admin/filme/{id}"})
    public ModelAndView form(@PathVariable("id") Optional<Integer> id, Filme filme){

        ModelAndView modelAndView = new ModelAndView("filme/filme");

        if (id.isPresent()){
            filme = filmeDao.findOne(id.get());
        }

        modelAndView.addObject("filme", filme);

        return modelAndView;
    }


    @PostMapping("/admin/filme")
    @Transactional
    public ModelAndView salva(@Valid Filme filme, BindingResult result){

        if (result.hasErrors()) {
            return form(Optional.ofNullable(filme.getId()), filme);
        }

        filmeDao.save(filme);

        ModelAndView view = new ModelAndView("redirect:/admin/filmes");

        return view;
    }


    @GetMapping(value="/admin/filmes")
    public ModelAndView lista(){

        ModelAndView modelAndView = new ModelAndView("filme/lista");

        modelAndView.addObject("filmes", filmeDao.findAll());

        return modelAndView;
    }


    @DeleteMapping("/admin/filme/{id}")
    @ResponseBody
    @Transactional
    public void delete(@PathVariable("id") Integer id){
        filmeDao.delete(id);
    }
    
    @GetMapping("/filme/em-cartaz")
    public ModelAndView emCartaz(){
    	ModelAndView modelAndView = new ModelAndView("filme/em-cartaz");
    	modelAndView.addObject("filmes", filmeDao.findAll());
    	return modelAndView;
    }
    
    @GetMapping("/filme/{id}/detalhe")
    public ModelAndView detalhe(@PathVariable("id") Integer id){
    	ModelAndView modelAndView = new ModelAndView("filme/detalhe");
    	Filme filme = filmeDao.findOne(id);
    	List<Sessao> sessoes = sessaoDao.buscaSessoesDoFilme(filme);
    	modelAndView.addObject("sessoes", sessoes);
    	return modelAndView;
    }
    
    private static String retornaNome(Filme filme){
    	return filme.getNome();
    }
    
    public static void main(String[] args) {
    	List<Filme> filmes = new ArrayList<>();
    	BigDecimal precoPadrao = new BigDecimal("12.0");
    	
    	Filme filme1 = new Filme("duro de matar", Duration.ofMinutes(120), "Ação", precoPadrao);
    	Filme filme2 = new Filme("poiu", Duration.ofMinutes(160), "Drama", precoPadrao);
    	Filme filme3 = new Filme("qwert", Duration.ofMinutes(90), "Terror", precoPadrao);
    	
    	filmes.add(filme1);
    	filmes.add(filme2);
    	filmes.add(filme3);
    	
    	for (Filme filme : filmes) {
			System.out.println(filme.getNome());
		}
    	
//    	filmes.removeIf(filme -> filme.getNome().startsWith("d"));
//    	
//    	filmes.forEach(filme -> System.out.println(filme.getNome()));
    	
    	
    	filmes.sort(new FilmeComparatorPorDuracao());
    	
    	
    	
    	List<String> nomes = filmes.stream().map(filme -> filme.getNome()).collect(Collectors.toList());
    	
    	nomes.forEach(nome -> System.out.println(nome));
	}

}
