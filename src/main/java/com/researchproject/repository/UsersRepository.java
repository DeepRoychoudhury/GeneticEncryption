package com.researchproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.researchproject.model.Encrypt;

@Repository
public interface UsersRepository extends JpaRepository<Encrypt, Integer>{
	
}
