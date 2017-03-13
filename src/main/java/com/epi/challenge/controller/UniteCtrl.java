package com.epi.challenge.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.epi.challenge.domain.Unite;
import com.epi.challenge.service.IUniteService;

/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/unite-services")
public class UniteCtrl {
	@Autowired
	private IUniteService uniteServ;

	@RequestMapping(value = "/getUnite", method = RequestMethod.GET)
	public Unite getUnite(Long id) {
		return uniteServ.getUniteById(id);
	}

	@RequestMapping(value = "/addUnite", method = RequestMethod.POST)
	public Unite addUnite(Unite unite) {
		uniteServ.save(unite);
		return unite;
	}

	@RequestMapping(value = "/editUnite", method = RequestMethod.PUT)
	public Unite editUnite(Unite unite) {
		uniteServ.save(unite);
		return unite;
	}

	@RequestMapping(value = "/deleteUnite", method = RequestMethod.DELETE)
	public void deleteUnite(Unite unite) {
		uniteServ.delete(unite);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Unite> getAll() {

		return uniteServ.getAll();
	}
	@RequestMapping(value = "/getAll{page}", method = RequestMethod.GET)
	public Page<Unite> getAllByPage(int page) {
		return uniteServ.getAll(page, 10);
	}

	public IUniteService getUniteServ() {
		return uniteServ;
	}

	public void setUniteServ(IUniteService uniteServ) {
		this.uniteServ = uniteServ;
	}
	

}
