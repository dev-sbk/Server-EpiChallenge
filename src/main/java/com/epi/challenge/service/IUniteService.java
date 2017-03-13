package com.epi.challenge.service;
import java.util.List;
import org.springframework.data.domain.Page;
import com.epi.challenge.domain.Unite;


public interface IUniteService {
	public Unite save(Unite u);
	public Unite update(Unite u);
	public void delete(Unite u);
	public Unite getUniteById(Long id);
	public List<Unite> getAll();
	public Page<Unite> getAll(int page, int size);
}
