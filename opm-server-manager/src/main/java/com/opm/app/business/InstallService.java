/**
 * 
 */
package com.opm.app.business;

import java.util.Map;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.opm.app.model.server.Server;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
public interface InstallService {
	
	public Session sshConnect(Server server) throws JSchException;
	
	public Map<String,String> copyInstallFiles(Session session , String destinationFile);
	
	public Map<String,String> mainInstall(Server server);
	
	public Map<String, String> execCommand(Session session, String command);
	
}
