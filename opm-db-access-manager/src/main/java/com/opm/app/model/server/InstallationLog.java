package com.opm.app.model.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.opm.app.model.SupperClass;

@Entity
public class InstallationLog extends SupperClass{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serviceName;
	private boolean installed;
	@Column(length=5000)
	private String comment;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="server_id")
	private Server server;

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public boolean isInstalled() {
		return installed;
	}
	public void setInstalled(boolean installed) {
		this.installed = installed;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	
}
