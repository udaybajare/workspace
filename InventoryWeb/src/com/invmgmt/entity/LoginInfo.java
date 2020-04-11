package com.invmgmt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginInfo {
	@Id
	private String userName;

	private String password;

	public LoginInfo(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public LoginInfo() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
