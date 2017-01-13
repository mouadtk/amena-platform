/**
 * 
 */
package com.opm.app.business.implementation;

import java.util.Map;

import com.opm.app.business.SystemConfig;
import com.opm.app.model.server.Server;

/**
 * @author Mouad-tk
 *
 * 9 janv. 2017
 */
public class SystemConfImpl implements SystemConfig{

	/***
	 * @return  µCPU, Memory, Disc, Bandwidth
	 */
	@Override
	public Map<String, String> systemState(Server serv) {
		
		return null;
	}

	/***
	 * @return serviceName, state.
	 */
	@Override
	public Map<String, String> serviceStates(Server serv) {
		
		return null;
	}

}
