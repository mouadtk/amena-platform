/**
 * 
 */
package com.opm.app.pmta;

import java.util.List;

import com.opm.app.model.Server;
 
/**
 * @author Mouad-tk
 *
 * 20 déc. 2016
 */
public interface PmtaLogService {
	
	List<String> logbByJobID(Server serv, String jobID);
}
