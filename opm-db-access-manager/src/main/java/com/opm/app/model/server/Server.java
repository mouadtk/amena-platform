/**
 * 
 */
package com.opm.app.model.server;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.opm.app.model.SupperClass;
import com.opm.app.model.enumeration.InstallationStatus;
import com.opm.app.model.enumeration.ServerState;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
@Entity
public class Server extends SupperClass{

	private static final long serialVersionUID = 1L;

	private String name;
	private String mainip;
	private int portssh;
	private String password;
	private String hostname;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="varchar(32) default 'New'")
	private ServerState state;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="varchar(32) default 'WAITING'")
	private InstallationStatus installState;
	@ManyToOne
	@JoinColumn(nullable=true)
	ServerProvider provider;
	@ManyToOne
	DomainProvider domainProvider;
	@OneToMany(mappedBy = "server", cascade=CascadeType.ALL)
	Set<ServerIP> ipserver;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainip() {
		return mainip;
	}
	public void setMainip(String mainip) {
		this.mainip = mainip;
	}
	public int getPortssh() {
		return portssh;
	}
	public void setPortssh(int portssh) {
		this.portssh = portssh;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ServerState getState() {
		return state;
	}
	public void setState(ServerState state) {
		this.state = state;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public ServerProvider getProvider() {
		return provider;
	}
	public void setProvider(ServerProvider provider) {
		this.provider = provider;
	}
	public DomainProvider getDomainProvider() {
		return domainProvider;
	}
	public void setDomainProvider(DomainProvider domainProvider) {
		this.domainProvider = domainProvider;
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
	public InstallationStatus getInstallState() {
		return installState;
	}
	public void setInstallState(InstallationStatus installState) {
		this.installState = installState;
	}
	
}