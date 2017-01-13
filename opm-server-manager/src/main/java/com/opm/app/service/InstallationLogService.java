package com.opm.app.service;

import java.util.List;

import com.opm.app.model.server.InstallationLog;

public interface InstallationLogService {

	public boolean saveOrupdate(InstallationLog installationLog);
	public List<InstallationLog> getAllLog();
	public List<InstallationLog> getAllLogByServer(long id);
}
