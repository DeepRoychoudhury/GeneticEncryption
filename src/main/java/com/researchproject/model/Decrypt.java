package com.researchproject.model;

public class Decrypt {

	private int user_id;
	private int file_id;
	private String group_id;
	private String password;
	private String data;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFileid() {
		return file_id;
	}
	public void setFileid(int file_id) {
		this.file_id = file_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
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
	public void setData(String data) {
		this.data = data;
	}
	
}
