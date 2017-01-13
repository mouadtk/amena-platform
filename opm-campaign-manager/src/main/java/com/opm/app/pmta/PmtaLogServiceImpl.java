/**
 * 
 */
package com.opm.app.pmta;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.opm.app.model.Server;

/**
 * @author Mouad-tk
 *
 * 20 déc. 2016
 */
@Service
public class PmtaLogServiceImpl implements PmtaLogService{
	
	@Override
	public List<String> logbByJobID(Server serv, String jobID) {
		try {
			RestTemplate rest = new RestTemplate();
			rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			rest.getMessageConverters().add(new StringHttpMessageConverter());
			String[] resp =  rest.getForObject("http://"+serv.getMainip()+":5524/api/xJobLog/"+jobID, String[].class);
			return Arrays.asList(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
