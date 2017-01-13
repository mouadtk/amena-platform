package com.opm.app.dao;

import java.util.List;

import com.opm.app.model.server.InstallationLog;

public interface InstallationLogDao {

	public boolean saveOrupdate(InstallationLog installationLog);
	public List<InstallationLog> getAllLog();
	public List<InstallationLog> getAllLogByServer(long id);
}
