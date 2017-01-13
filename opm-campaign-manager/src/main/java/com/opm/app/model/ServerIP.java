/**
 * 
 */
package com.opm.app.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
public class ServerIP extends SupperClass{
	
	private String ip;
	private String domain;	
	private boolean rDNS;
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
