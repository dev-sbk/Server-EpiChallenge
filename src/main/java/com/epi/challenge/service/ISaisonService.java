package com.epi.challenge.service;
import java.util.List;
import org.springframework.data.domain.Page;
import com.epi.challenge.domain.Saison;


public interface ISaisonService {
	public Saison save(Saison u);
	public Saison update(Saison u);
	public void delete(Saison u);
	public Saison getSaisonById(Long id);
	public List<Saison> getAll();
	public Page<Saison> getAll(int page, int size);
}
