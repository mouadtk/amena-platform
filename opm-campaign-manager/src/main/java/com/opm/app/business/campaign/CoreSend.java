/**
 * 
 */
package com.opm.app.business.campaign;

import java.net.Socket;

/**
 * @author Mouad-tk
 *
 * 3 janv. 2017
 * gha 7chemt nsmiha ...
 */
public interface CoreSend {

	public String sendMessage(Socket session, String mailfrom,String rcptto, String message) throws Exception;
	public Socket connect(String mailHost, int smtpPort, int timeOut) throws Exception;
	
}
