package com.opm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.dao.InstallationLogDao;
import com.opm.app.model.server.InstallationLog;
import com.opm.app.service.InstallationLogService;

@Service
@Transactional
public class InstallationLogServiceImpl implements InstallationLogService{

	@Autowired
	private InstallationLogDao dao;
	
	@Override
	public boolean saveOrupdate(InstallationLog installationLog) {
		return dao.saveOrupdate(installationLog);
	}

	@Override
	public List<InstallationLog> getAllLog() {
		return dao.getAllLog();
	}

	@Override
	public List<InstallationLog> getAllLogByServer(long id) {
		return dao.getAllLogByServer(id);
	}

}
