package com.epi.challenge.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.epi.challenge.domain.Client;
import com.epi.challenge.service.IClientService;
/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/client-services")
public class ClientCtrl {
	@Autowired
	private IClientService clientServ;

	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getLogedUser(HttpServletRequest httpServletRequest) {
		Map<String, Object> params = new HashMap<String, Object>();
		HttpSession session = httpServletRequest.getSession();
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = securityContext.getAuthentication().getName();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga : securityContext.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		params.put("username", username);
		params.put("roles", roles);
		return params;
	}
	@RequestMapping(value = "/getClient", method = RequestMethod.GET)
	public Client getClient(Long id) {
		return clientServ.getClientById(id);
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public Client addClient(Client client) {
		clientServ.save(client);
		return client;
	}

	@RequestMapping(value = "/editClient", method = RequestMethod.PUT)
	public Client editClient(Client client) {
		clientServ.save(client);
		return client;
	}

	@RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE)
	public void deleteClient(Client client) {
		clientServ.delete(client);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Client> getAll() {

		return clientServ.getAll();
	}
	@RequestMapping(value = "/getAll{page}", method = RequestMethod.GET)
	public Page<Client> getAllByPage(int page) {
		return clientServ.getAll(page, 10);
	}
	/**
	 * Cette méthode permet de crypter le mot de passe en MD5
	 * 
	 * @param password
	 * @return
	 */
	public String md5PasswordEncoder(String password) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, null);
	}

	public IClientService getClientServ() {
		return clientServ;
	}

	public void setClientServ(IClientService clientServ) {
		this.clientServ = clientServ;
	}

}
