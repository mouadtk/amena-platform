/**
 * 
 */
package com.opm.app.business.dns;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.opm.app.model.server.ServerIP;

/**
 * @author Mouad-tk
 *
 *         25 nov. 2016
 */
@Service("nameCheapService")
@PropertySource(value = { "classpath:dns_Api.properties" })
public class NameCheap implements SettingDNS {

	@Override
	public Set<String> getFreeDomains(int nbr) {
		return null;
	}
	
	@Value("namecheap_username")
	private String username;
	
	@Value("namecheap_apiKey")
	private String apiKey;
	
	@Override
	public Map<String, String> setDomainIP(Set<ServerIP> ips, String domain, String dkim) {
		
		@SuppressWarnings("unused")
		String hosts="";
		@SuppressWarnings("unused")
		String spfValue="v=spf1 ";
		int i=0;
		for (ServerIP subIp : ips) {
			spfValue+="ip4:"+subIp.getIp().trim()+" ";
			i++;
			if(subIp.getDomain().split(".").length==3){
				hosts+="&HostName"+i+"="+subIp.getDomain().split(".")[0].trim()
						+ "&RecordType"+i+"=A&Address"+i+"="+subIp.getIp().trim()+"&TTL"+i+"=60000";
				i++;
				hosts+="&HostName"+i+"="+subIp.getDomain().split(".")[0].trim()
						+ "&RecordType"+i+"=MX&Address"+i+"="+subIp.getIp().trim()+"&TTL"+i+"=60000";
			}
			else if  (subIp.getDomain().split(".").length==2){
				
				hosts+="&HostName"+i+"=www"
						+ "&RecordType"+i+"=A&Address"+i+"="+subIp.getIp().trim()+"&TTL"+i+"=60000";
				i++;
				hosts+="&HostName"+i+"=*"
						+ "&RecordType"+i+"=A&Address"+i+"="+subIp.getIp().trim()+"&TTL"+i+"=60000";
				i++;
				hosts+="&HostName"+i+"=@"
				+ "&RecordType"+i+"=MX&Address"+i+"="+subIp.getIp().trim()+"&MXPref"+i+"=1"+"&TTL"+i+"=60000";
			}
		}
		
		String _dkim=""+dkim;
		String dmarc;
		spfValue+=" -all";
		
		return null;
	}
}