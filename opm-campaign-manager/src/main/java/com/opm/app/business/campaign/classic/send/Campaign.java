/**
 * 
 */
package com.opm.app.business.campaign.classic.send;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Mouad-tk
 *
 * 2 janv. 2017
 */
@Service
@Scope("prototype")
public class Campaign {
	
	Map<String, CampaignThread> threads;
	
}