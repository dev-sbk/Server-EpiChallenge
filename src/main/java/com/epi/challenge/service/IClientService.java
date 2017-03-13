package com.epi.challenge.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.epi.challenge.domain.Client;


public interface IClientService {
	public Client save(Client u);
	public Client update(Client u);
	public void delete(Client u);
	public Client getClientById(Long id);
	public List<Client> getAll();
	public Page<Client> getAll(int page, int size);
}
