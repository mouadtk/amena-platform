/**
 * 
 */
package com.opm.app.model.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.opm.app.model.SupperClass;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
@Entity
public class ServerIP extends SupperClass{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	private String domain;
	
	@Column(columnDefinition="tinyint(1) default 0")
	private boolean rDNS;
	@ManyToOne
	Server server;
		
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public boolean isrDNS() {
		return rDNS;
	}
	public void setrDNS(boolean rDNS) {
		this.rDNS = rDNS;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	
}
