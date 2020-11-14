package com.researchproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.model.FilesTable;
import com.researchproject.repository.FilesRepository;

@Service
public class FilesService {

	@Autowired
	FilesRepository filesrepo;
	
	public boolean enterFileRecord(FilesTable file) {
		filesrepo.save(file);
		return true;
	}
}
