/**
 * 
 */
package com.opm.app.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.dao.ServerDao;
import com.opm.app.model.enumeration.InstallationStatus;
import com.opm.app.model.enumeration.ServerState;
import com.opm.app.model.server.Domain;
import com.opm.app.model.server.Server;
import com.opm.app.model.server.ServerIP;
import com.opm.app.model.server.ServerProvider;
import com.opm.app.service.IpService;
import com.opm.app.service.ServerProviderService;
import com.opm.app.service.ServerService;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
@Transactional
@Service
@PropertySource("classpath:application.properties")
public class ServerServiceImpl implements ServerService  {
	
	@Value("subdomain.dict")
	String SUBDOMAIN_DICT;
	
	@Autowired
	private ServerDao dao;
	
	@Autowired
	ServerProviderService providerService;
	
	@Autowired
	IpService ipService;
	
	@Override
	public List<Server> getALLServers() {
		return dao.getALLServers();
	}

	@Override
	public boolean saveOrUpdate(Server server) {
		return dao.saveOrUpdate(server);
	}

	@Override
	public Server getCompleteServerInfos(long id) {
		try{
			Server server = dao.findById(id, false);
			return server;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null; 
	}
	
	@Override
	public Server findById(long id , boolean lazyMode) {
		return dao.findById(id , lazyMode);
	}
	
	@Override
	public Set<ServerIP> subDomainGenerator(List<Domain> domains, List<String> ips_tmp, Server srv) {

		try{
			System.out.println(SUBDOMAIN_DICT);
			Resource resource = new ClassPathResource("/"+SUBDOMAIN_DICT);
			//{ "comfort", "communicate", "confidence", "powerful", "mail", "it", "shore", "good", "sure", "sub", "us", "sub22", "way", "at", "your" };
			@SuppressWarnings("deprecation")
			List<String> keywords =  Files.readAllLines(Paths.get(resource.getFile().getAbsolutePath()), Charsets.UTF_8);
			int indiceDomain = 0;
			Set<ServerIP> ips = new HashSet<ServerIP>();
			List<String> usedKey = new ArrayList<>();
			for (int i = 0; i < ips_tmp.size(); i++) {
				if (i != 0 && i % 4 == 0) {
					indiceDomain++;
					usedKey = new ArrayList<>();
				}
				ServerIP ip = new ServerIP();
				ip.setIp(ips_tmp.get(i).trim());
				ip.setActif(true);
				ip.setrDNS(false);
				ip.setServer(srv);
				if(domains!=null){
					if (i % 4 == 0) {
						ip.setDomain(domains.get(indiceDomain).getDomain());
					} else {
						String sub;
						Random r = new Random();
						while (usedKey.contains(sub = keywords.get(r.nextInt(keywords.size() - 1)))) {
							System.out.println(sub);
						}
						usedKey.add(sub);
						ip.setDomain(sub + "." + domains.get(indiceDomain).getDomain());
					}
				}
				ipService.savOrUpdate(ip);
				ips.add(ip);
			}
			return ips;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Server findByMainIp(String mainIp) {
		return dao.findByMainIp(mainIp);
	}

	/***
	 * 
	 */
	@Override
	public Server saveOrUpdate(HttpServletRequest req) {
		try {
			ServerProvider provider = providerService.findById(Long.valueOf(req.getParameter("provider")));
			Server srv = new Server();
			srv.setActif(true);
			srv.setMainip(req.getParameter("main_ip"));
			srv.setPassword(req.getParameter("password"));
			srv.setPortssh(Integer.valueOf(req.getParameter("sshport")));
			srv.setProvider(provider);
			srv.setInstallState(InstallationStatus.WAITING);
			srv.setState(ServerState.New);	
			return srv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<Long, String> checkServersProcess(List<Long> ids) {
		List<Server> servers=dao.getALLServers(ids);
		Map<Long, String> map= new HashMap<>();
		try {
			for (Server server : servers) {
				map.put(server.getId(), server.getInstallState().name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<Server> getNewServerOrFailedInstalltion() {
		return dao.getNewServerOrFailedInstalltion();
	}
	

}
