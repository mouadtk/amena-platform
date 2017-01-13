/**
 * 
 */
package com.opm.app.model.server;

import javax.persistence.Entity;

import com.opm.app.model.SupperClass;

/**
 * @author Mouad-tk
 * 25 nov. 2016
 */
@Entity
public class DomainProvider extends SupperClass{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String name ;
	private String login;
	private String password;
	private String apiKey;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	} 
}
