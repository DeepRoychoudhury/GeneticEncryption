package com.researchproject.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.researchproject.model.Users;
import com.researchproject.repository.UsersRepository;

@Service
public class UsersService {

	private Connection con;
	
	
	private Connection connect() throws ClassNotFoundException, SQLException {
		
		  String url = "jdbc:postgresql://encdb.cpwytlyekgvv.us-east-1.rds.amazonaws.com/encdb";
		  //String url = "jdbc:postgresql://localhost:5432/encdb"; 
		  String user = "postgres"; 
		  String password = "postgres";
		  Class.forName("org.postgresql.Driver");
		 
		con = DriverManager.getConnection(url, user, password); 
		return con;
	}
	  
	@Autowired
	UsersRepository usersrepo;
	
	public boolean enterNewUser(Users users) {
		boolean isUserAdmin = false;
		boolean isAdminPasswordCorrect = false;
		isUserAdmin = checkIfUserisAdmin(users);
		isAdminPasswordCorrect = checkAdminAuth(users);
		if((isUserAdmin == true) && (isAdminPasswordCorrect==true))
		{	
		users.setGroup_id(fetchGroup_id(users));
		if(users.getGroup_id() != 0) {
			usersrepo.save(users);
		}
		return true;
		}
		return false;
	}
	
	private int fetchGroup_id(Users users) {
		int group_id = 0;
		String sb = "select group_id from group_table where group_name='"+users.getGroup_name()+"'";
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(sb);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			group_id = rs.getInt(1);
			}
			if(group_id != 0) return group_id;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private boolean checkAdminAuth(Users users) {
		int user_id = users.getUser_id();
		int fetchedUser_id = user_id;
		String sb = "select user_id from users where user_name='"+users.getAdmin_username()+"' and password='"+users.getAdmin_password()+"'";
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(sb);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				fetchedUser_id = rs.getInt(1);
			}
			if(user_id != fetchedUser_id) return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean checkIfUserisAdmin(Users users) {
		String user_id = "N";
		String sb = "select isAdmin from users where user_name='"+users.getAdmin_username()+"'";
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(sb);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			user_id = rs.getString(1);
			}
			if(user_id.equals("Y")) return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
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
