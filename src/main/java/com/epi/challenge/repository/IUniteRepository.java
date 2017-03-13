package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.epi.challenge.domain.Unite;

@Repository
public interface IUniteRepository extends JpaRepository<Unite, Long> {

}
