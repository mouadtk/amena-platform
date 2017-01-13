package com.opm.app.dao;

import java.util.List;

import com.opm.app.model.server.Domain;
import com.opm.app.model.server.DomainProvider;

public interface DomainDao {

	public List<Domain>  getFreeDomains(int nbr);
	public boolean saveOrUpdate(Domain domain);
	public List<Domain> getDomainsGoupByProvider(int nbr, long idProvider);
	public DomainProvider getProvider();
	
}
