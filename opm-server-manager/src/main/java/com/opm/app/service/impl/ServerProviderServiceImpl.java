package com.opm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.dao.ServerProviderDao;
import com.opm.app.model.server.ServerProvider;
import com.opm.app.service.ServerProviderService;

@Service
@Transactional
public class ServerProviderServiceImpl implements ServerProviderService{

	@Autowired
	private ServerProviderDao dao;

	@Override
	public ServerProvider findById(long id) {
		return dao.findById(id);
	}

	@Override
	public ServerProvider findName(String name) {
		return dao.findName(name);
	}

	@Override
	public List<ServerProvider> getAllProviders() {
		return dao.getAllProviders();
	}
	
}
