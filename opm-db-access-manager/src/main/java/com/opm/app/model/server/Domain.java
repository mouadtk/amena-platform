package com.opm.app.model.server;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.opm.app.model.SupperClass;
import com.opm.app.model.enumeration.DomainState;

@Entity
public class Domain extends SupperClass{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String domain; 
	
	@ManyToOne
	@JoinColumn(name="provider_id", nullable=false)
	private DomainProvider provider;
	
	private Date dateExpiration;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="varchar(32) default 'New'")
	private DomainState state;

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Date getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public DomainState getState() {
		return state;
	}
	public void setState(DomainState state) {
		this.state = state;
	}
	public DomainProvider getProvider() {
		return provider;
	}
	public void setProvider(DomainProvider provider) {
		this.provider = provider;
	}
}
