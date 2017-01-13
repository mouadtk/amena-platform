package com.opm.app.service;

import com.opm.app.model.server.ServerIP;

public interface IpService {

	public boolean savOrUpdate(ServerIP ip);
}
