/**
 * 
 */
package com.opm.app.business.campaign.classic.send;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author Mouad-tk
 *
 * 2 janv. 2017
 */
@Service
public class CampaignFactory {

	private Map<String, Object> AllCampaigns = new HashMap<String, Object>();

	public Object addCampaign(String __key,Object campaign){		
		
		return AllCampaigns.put(__key, campaign);
	}

}