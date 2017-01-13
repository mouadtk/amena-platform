package com.opm.app.service;

import java.util.List;

import com.opm.app.model.server.ServerProvider;

public interface ServerProviderService {
	
	public ServerProvider findById(long id);
	public ServerProvider findName(String name);
	public List<ServerProvider> getAllProviders();
}
