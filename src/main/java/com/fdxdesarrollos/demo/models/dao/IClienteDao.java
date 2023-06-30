package com.fdxdesarrollos.demo.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fdxdesarrollos.demo.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	/*public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void delete(Long id);*/
}
