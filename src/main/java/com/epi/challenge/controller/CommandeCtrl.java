package com.epi.challenge.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epi.challenge.domain.Commande;
import com.epi.challenge.domain.LigneCommande;
import com.epi.challenge.service.ICommandeService;
import com.epi.challenge.service.ILigneCommandeService;

/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/commande-services")
public class CommandeCtrl {
	@Autowired
	private ICommandeService commandeServ;
	@Autowired
	private ILigneCommandeService ligneCommandeService;

	@RequestMapping(value = "/getProduit", method = RequestMethod.GET)
	public Commande getCommande(Long id) {
		return commandeServ.getCommandeById(id);
	}
	@RequestMapping(value = "/getCommande{id}", method = RequestMethod.GET)
	public List<LigneCommande> getAll(Long id) {
		return ligneCommandeService.findByCommande(commandeServ.getCommandeById(id));
	}

	public ICommandeService getCommandeServ() {
		return commandeServ;
	}

	public void setCommandeServ(ICommandeService commandeServ) {
		this.commandeServ = commandeServ;
	}

	public ILigneCommandeService getLigneCommandeService() {
		return ligneCommandeService;
	}

	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

}
