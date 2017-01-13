/**
 * 
 */
package com.opm.app.business.globaltest;

import java.net.Socket;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opm.app.business.globaltest.beans.MessageSettings;
import com.opm.app.business.globaltest.beans.TestSettings;
import com.opm.app.model.Server;

/***
 * @author Mouad-tk
 *
 * 30 nov. 2016
 **/
public interface TestServcie {

	public  String sendMessage(Socket session, String mailfrom,String rcptto, String message);
	public List<Server> getServersforTest(HttpServletRequest request);
	public TestSettings testSettingsFactory(HttpServletRequest request);
	/**
	 * @param serv
	 * @param messageSettings
	 * @param testSettings
	 */
	public void sendMessage(Server serv, MessageSettings messageSettings, TestSettings testSettings);	
	String setTags(String message , String tagName ,List<String> listOptions, int index);
	
}
