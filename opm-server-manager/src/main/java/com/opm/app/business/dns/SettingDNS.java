/**
 * 
 */
package com.opm.app.business.dns;

import java.util.Map;
import java.util.Set;

import com.opm.app.model.server.ServerIP;

/**
 * @author Mouad-tk
 *
 * 25 nov. 2016
 */
public interface SettingDNS {
	
	public Set<String> getFreeDomains(int nbr);
	public Map<String, String> setDomainIP(Set<ServerIP> ips, String domain, String dkim);

}
