package com.fdxdesarrollos.demo.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fdxdesarrollos.demo.models.entity.Cliente;

//@Repository("clienteDaoJPA")
/*@Repository
public class ClienteDaoImp implements IClienteDao{
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}
	
	@Override
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}	

	@Override
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		} else {
			em.persist(cliente);
		}
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}*/
