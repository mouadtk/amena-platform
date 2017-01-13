package com.opm.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opm.app.dao.IpDao;
import com.opm.app.model.server.ServerIP;
import com.opm.app.service.IpService;

@Service
public class IpServiceImpl implements IpService{

	@Autowired
	IpDao dao;

	@Override
	public boolean savOrUpdate(ServerIP ip) {
		return dao.saveOrUpdate(ip);
	}
	
}
