package com.researchproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.researchproject.model.Encrypt;
import com.researchproject.model.FilesTable;

@Repository
public interface FilesRepository extends CrudRepository<Encrypt, Long>{

	void save(FilesTable file);

}
