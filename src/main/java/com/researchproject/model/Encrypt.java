//Pojo Class for Encryption
package com.researchproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Encrypt {

	@Id
	private int user_id;
	@Column(name = "user_name")
	private String user_name;
	private String file_name;
	private String data;
	@Column(name = "group_id")
	private int group_id;
	@Column(name = "password")
	private String password;
	private String group_name;	
	@Column(name = "isDataOwner")
	private String isDataOwner;
	@Column(name = "isAdmin")
	private String isAdmin;

	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getIsDataOwner() {
		return isDataOwner;
	}
	public void setIsDataOwner(String isDataOwner) {
		this.isDataOwner = isDataOwner;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getData() {
		return data;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	
	
}
