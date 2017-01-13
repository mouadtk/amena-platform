/**
 * 
 */
package com.opm.app.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.opm.app.model.server.Domain;
import com.opm.app.model.server.Server;
import com.opm.app.model.server.ServerIP;

/**
 * @author Mouad-tk
 *
 * 5 déc. 2016
 */


public interface ServerService {
	
	public List<Server>getALLServers();
	public boolean saveOrUpdate(Server server);
	/**
	 * @param id
	 * @return Server & ServerIP 
	 */
	public Server getCompleteServerInfos(long id);
	public Server saveOrUpdate(HttpServletRequest req);
	public Server findById(long id,  boolean lazyMode);
	
	public Set<ServerIP> subDomainGenerator(List<Domain> domains, List<String> ips_tmp, Server srv);
	public Server findByMainIp(String mainIp);
	public Map<Long, String> checkServersProcess(List<Long> ids);
	public List<Server> getNewServerOrFailedInstalltion();
}
