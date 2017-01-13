package com.opm.app.dao;

import java.util.List;

import com.opm.app.model.server.Server;

public interface  ServerDao {
	
	public boolean saveOrUpdate(Server server);
	public Server findById(long id, boolean lazyMode);
	public List<Server> getALLServers();
	public Server findByMainIp(String mainIp);
	public List<Server> getALLServers(List<Long> ids);
	public List<Server> getNewServerOrFailedInstalltion();
}
