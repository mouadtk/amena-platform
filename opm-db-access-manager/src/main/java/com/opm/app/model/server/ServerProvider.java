/**
 * 
 */
package com.opm.app.model.server;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.opm.app.model.SupperClass;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
@Entity
public class ServerProvider extends  SupperClass{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String webSite;
	private String userAccount;
	private String password;
	
	@OneToMany(mappedBy= "provider")
	Set<Server> servers;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Server> getServers() {
		return servers;
	}
	public void setServers(Set<Server> servers) {
		this.servers = servers;
	}

}
