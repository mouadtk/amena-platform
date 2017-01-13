package com.opm.app.service;

import java.util.List;

import com.opm.app.model.server.Domain;

public interface DomainService {

	public List<Domain>  getFreeDomains(int nbr, int provider);
	public boolean saveOrUpdate(Domain domain);

}
