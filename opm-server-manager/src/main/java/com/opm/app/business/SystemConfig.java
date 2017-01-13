/**
 * 
 */
package com.opm.app.business;

import java.util.Map;

import com.opm.app.model.server.Server;

/***
 * @author Mouad-tk
 *
 * 9 janv. 2017
 */
public interface SystemConfig {
	
	/***
	 * @return  µCPU, Memory, Disc, Bandwidth
	 */
	Map<String, String> systemState(Server serv);
	/***
	 * @return serviceName, state.
	 */
	Map<String, String> serviceStates(Server serv);

}