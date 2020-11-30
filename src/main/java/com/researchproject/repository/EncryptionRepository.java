package com.researchproject.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EncryptionRepository {

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
	
	public int fetchDataOwnerId(String user_name) throws SQLException, ClassNotFoundException{
		int dataOwner_id = 0;
		String sb = "select user_id from users where user_name='"+user_name+"'";
		connect();
		PreparedStatement ps = con.prepareStatement(sb);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		dataOwner_id = rs.getInt(1);
		}
		return dataOwner_id;		
	}
	
	public int fetchGroupId(String group_name) throws ClassNotFoundException, SQLException {
		int group_id = 0;
		String sb = "select group_id from group_table where group_name='"+group_name+"'";
		connect();
		PreparedStatement ps = con.prepareStatement(sb);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		group_id = rs.getInt(1);
		}
		return group_id;
	}
}
