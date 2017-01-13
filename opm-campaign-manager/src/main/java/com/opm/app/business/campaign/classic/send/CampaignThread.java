/**
 * 
 */
package com.opm.app.business.campaign.classic.send;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opm.app.business.campaign.CampaignSendProtocole;
import com.opm.app.business.campaign.beans.MessageSettings;
import com.opm.app.business.campaign.beans.SendSettings;
import com.opm.app.model.ServerIP;
/**
 * @author Mouad-tk
 *
 * 2 janv. 2017
 */
@Component
@Scope("prottype")
public class CampaignThread extends Thread implements CampaignSendProtocole{

	ServerIP ip;
	String dataListName;
	int indexBegin;
	boolean running;
	MessageSettings msgSettings;
	SendSettings sendSettings;
	
	
	/**
	 * 
	 */
	public CampaignThread(ServerIP __ip, String __datalist, MessageSettings __msgSettings, SendSettings __sendSettings) {
		running 		= true;
		ip 				=  __ip;
		dataListName	= __datalist;
		msgSettings 	= __msgSettings;
		sendSettings 	= __sendSettings;
	}
	
	@Override
	public void run(){
		
		startCampaign();
	}


	@Override
	public void startCampaign() {
		
		/***
		 * load email data 
		 **/
		List<String> emailList = getDataList(dataListName);
		while(running){
			
		}
	}


	@Override
	public void pauseCampaign(long campaignID) {
		
		this.running = false;
		
	}

	
	@Override
	public void stopCampaign(long campaignID) {
		this.running = false;
	}

	@Override
	public void resumeCampaign(long campaignID) {
		
	}
	
	/***
	 * 
	 * @param listDataName
	 * @return list of emails (data)
	 */
	List<String> getDataList(String listDataName){
		return null;
	}
	
}
