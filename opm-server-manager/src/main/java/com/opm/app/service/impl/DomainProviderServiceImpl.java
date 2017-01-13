package com.opm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.dao.DomainProviderDao;
import com.opm.app.model.server.DomainProvider;
import com.opm.app.service.DomainProviderService;

@Transactional
@Service
public class DomainProviderServiceImpl implements DomainProviderService{

	@Autowired
	DomainProviderDao dao;
	
	@Override
	public List<DomainProvider> getAllProviders() {
		return dao.getAllProviders();
	}
	
}
