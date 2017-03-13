package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Client;
@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

}
