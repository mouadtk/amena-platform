/**
 * 
 */
package com.opm.app.business.globaltest;

import javax.servlet.http.HttpServletRequest;

import com.opm.app.business.globaltest.beans.MessageSettings;

/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 */

public interface MessageBuilder {
	
	public String messageBuild(HttpServletRequest data);

	/**
	 * @param request
	 * @return
	 */
	public MessageSettings msgSettingsFactory(HttpServletRequest request);
}
