/**
 * 
 */
package com.opm.app.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
public class Server extends SupperClass{

	private static final long serialVersionUID = 1L;
	private String mainip; 
	private String name;
	private Set<ServerIP> ipserver;
	
	public Server(){
		ipserver = new HashSet<ServerIP>();
	}
	/**
	 * @param string
	 */
	public Server(String string) {
		name = string;
		ipserver = new HashSet<ServerIP>();
	}
	
	public String getMainip() {
		return mainip;
	}
	public void setMainip(String mainip) {
		this.mainip = mainip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<ServerIP> getIpserver() {
		return ipserver;
	}
	public void setIpserver(Set<ServerIP> ipserver) {
		this.ipserver = ipserver;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
	
}