package com.epi.challenge.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epi.challenge.domain.Saison;
import com.epi.challenge.service.ISaisonService;


/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/saison-services")
public class SaisonCtrl {
	@Autowired
	private ISaisonService saisonService;

	@RequestMapping(value = "/getUnite", method = RequestMethod.GET)
	public Saison getSaison(Long id) {
		return saisonService.getSaisonById(id);
	}

	@RequestMapping(value = "/addSaison", method = RequestMethod.POST)
	public Saison addProduit(Saison saison) {
		saisonService.save(saison);
		return saison;
	}

	@RequestMapping(value = "/editSaison", method = RequestMethod.PUT)
	public Saison editSaison(Saison saison) {
		saisonService.save(saison);
		return saison;
	}

	@RequestMapping(value = "/deleteSaison", method = RequestMethod.DELETE)
	public void deleteSaison(Saison saison) {
		saisonService.delete(saison);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Saison> getAll() {

		return saisonService.getAll();
	}
	@RequestMapping(value = "/getAll{page}", method = RequestMethod.GET)
	public Page<Saison> getAllByPage(int page) {
		return saisonService.getAll(page, 10);
	}

	public ISaisonService getSaisonService() {
		return saisonService;
	}

	public void setSaisonService(ISaisonService saisonService) {
		this.saisonService = saisonService;
	}

	
	

}
