package com.researchproject.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.researchproject.model.Decrypt;

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
		con.close();
		return group_id;
	}
	
	public boolean checkUserGroup(Decrypt decrypt, String filename) {
		String group_name = null;
		boolean isUserPassOk = CheckUserPassword(decrypt.getUser_name(),decrypt.getPassword());
		boolean isUserGroupOk = CheckUserGroup(decrypt.getGroup_name(),decrypt.getUser_name());
		if(isUserPassOk == true && isUserGroupOk==true) {
		String query = "select group_name from group_table where group_id=(select group_id from files where file_name='"+filename+"')";
		
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				group_name = rs.getString(1);
			}
			if(group_name.equals(decrypt.getGroup_name())) {
				return true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return false;
	}

	private boolean CheckUserGroup(String group_name, String user_name) {
		boolean isUserGroupcorrect = false;
		String groupname = null;
		String query = "select group_name from group_table where group_id=(select group_id from users where user_name ='"+user_name+"')";
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				groupname = rs.getString(1);
			}
			if(group_name.equals(groupname)) {
				isUserGroupcorrect = true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUserGroupcorrect;
	}

	private boolean CheckUserPassword(String user_name, String password) {
		boolean isUserPassCorrect = false;
		int user_id=0;
		String query = "select user_id from users where user_name='"+user_name+"' AND password='"+password+"'";
		try {
			connect();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user_id = rs.getInt(1);
			}
			if(user_id != 0) {
				isUserPassCorrect = true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUserPassCorrect;
	}
}
