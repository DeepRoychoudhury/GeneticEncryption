package com.researchproject.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class DecryptionRepository {

	private Connection con;
	
	private Connection connect() throws ClassNotFoundException, SQLException {
		
		  String url = "jdbc:postgresql://localhost:5432/encdb"; 
		  String user = "postgres"; 
		  String password = "postgres";
		  Class.forName("org.postgresql.Driver");
		 
		con = DriverManager.getConnection(url, user, password); 
		return con;
	}
	
	public String getDataOwnerSecret(String file_name) throws ClassNotFoundException, SQLException {
		String dataOwnerSecret = null;
		String query = "select password from users where user_id=(select user_id from files where file_name='"+file_name+"')";
		connect();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		dataOwnerSecret = rs.getString(1);
		}
		return dataOwnerSecret;
	}
	
	public int getDataOwnerUserId(String file_name) throws ClassNotFoundException, SQLException {
		int user_id = 0;
		String query = "select user_id from files where file_name='"+file_name+"'";
		connect();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			user_id = rs.getInt(1);
		}
		return user_id;
	}
	
	public int getGroupId(String file_name) throws ClassNotFoundException, SQLException {
		int group_id = 0;
		String query = "select group_id from files where file_name='"+file_name+"'";
		connect();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			group_id = rs.getInt(1);
		}
		return group_id;
	}
}
