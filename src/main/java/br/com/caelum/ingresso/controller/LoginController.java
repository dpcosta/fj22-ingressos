package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.LoginDao;
import br.com.caelum.ingresso.model.Usuario;

@Controller
public class LoginController {

	@Autowired
	private LoginDao loginDao;
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
//	@PostMapping
//	public String login(Usuario usuario){
//		
//		return "redirect:/";
//	}
//	
//	@GetMapping("/usuario")
//	public String registro(){
//		return "/usuario/form";
//	}
//	
//	@PostMapping("/usuario")
//	@Transactional
//	public String registro(@Valid Usuario usuario, BindingResult result){
//		if (result.hasErrors()) return "/usuario/form";
//		loginDao.save(usuario);
//		return "/usuario/adicionado";
//	}
}
