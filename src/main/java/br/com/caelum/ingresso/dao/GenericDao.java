package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDao <T> {
	
	@PersistenceContext
	private EntityManager manager;

	public void save(T object){
		manager.persist(object);
	}
	
	public T findOne(Class<T> tClass, Integer id){
		return manager.find(tClass, id);
	}
	
	public List<T> findAll(Class<T> tClass){
		return manager
				.createQuery("select o from " + tClass.getName() + " o", tClass)
				.getResultList();
	}
	
}
