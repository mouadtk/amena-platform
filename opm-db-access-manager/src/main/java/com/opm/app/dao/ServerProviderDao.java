package com.opm.app.dao;

import java.util.List;

import com.opm.app.model.server.ServerProvider;

public interface ServerProviderDao {

	public ServerProvider findById(long id);
	public ServerProvider findName(String name);
	public List<ServerProvider> getAllProviders();

}
