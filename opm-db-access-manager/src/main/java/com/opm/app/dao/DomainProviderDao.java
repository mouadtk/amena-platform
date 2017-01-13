package com.opm.app.dao;

import java.util.List;

import com.opm.app.model.server.DomainProvider;

public interface DomainProviderDao {

	public List<DomainProvider> getAllProviders();
}
