package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Role;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

}
