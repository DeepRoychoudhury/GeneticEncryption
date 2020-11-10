package com.researchproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersrepo;
	
	/*
	 * public int fetchDataOwnerId(){ usersrepo.findById(id); }
	 * 
	 * public int fetchGroupId(String group_name) throws ClassNotFoundException,
	 * SQLException { int group_id = 0; String sb =
	 * "select group_id from group_table where group_name='"+group_name+"'";
	 * connect(); PreparedStatement ps = con.prepareStatement(sb); ResultSet rs =
	 * ps.executeQuery(); group_id = rs.getInt(0); return group_id; }
	 */
}
