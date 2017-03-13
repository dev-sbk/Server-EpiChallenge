package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.challenge.domain.Client;
import com.epi.challenge.repository.IClientRepository;
import com.epi.challenge.service.IClientService;

@Service
@Transactional
public class ClientService implements IClientService {
	@Autowired
	private IClientRepository repository;

	@Override
	public Client save(Client u) {
		repository.save(u);
		return u;
	}

	@Override
	public Client update(Client u) {
		repository.save(u);
		return u;
	}

	@Override
	public void delete(Client u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Client getClientById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Client> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Client> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

	public IClientRepository getRepository() {
		return repository;
	}

	public void setRepository(IClientRepository repository) {
		this.repository = repository;
	}

}
