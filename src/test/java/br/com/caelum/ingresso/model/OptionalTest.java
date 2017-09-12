package br.com.caelum.ingresso.model;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

public class OptionalTest {

	@Test
	public void quandoObjetoEhGeradoComEmptyIsPresentDeveRetornarFalse() {
		Optional<Object> objeto = Optional.empty();
		Assert.assertFalse(objeto.isPresent());
	}
	
	@Test(expected=NullPointerException.class)
	public void quandoObjetoEhNuloIsPresentDeveGerarNullPointerException(){
		Optional<Object> objeto = null;
		objeto.isPresent();
	}
	
	@Test
	public void ifPresentSohExecutaFuncaoQuandoOptionalIsPresent(){
		Optional<Object> objeto = Optional.of(new Integer(12));
		objeto.ifPresent(o -> confirmaQueObjetoNaoEhNulo(o));
		
		objeto = Optional.empty();
		objeto.ifPresent(o -> confirmaQueObjetoNaoEhNulo(o));
		Assert.assertFalse(objeto.isPresent());
	}
	
	private void confirmaQueObjetoNaoEhNulo(Object o){
		Assert.assertNotNull(o);
	}

}
