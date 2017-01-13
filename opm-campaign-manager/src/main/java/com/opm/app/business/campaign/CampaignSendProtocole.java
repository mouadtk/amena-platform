/**
 * 
 */
package com.opm.app.business.campaign;

/**
 * @author Mouad-tk
 *
 * 2 janv. 2017
 * 
 * contains rules must be implemented by every campaign  thread.
 * 
 * 
 */
 public interface CampaignSendProtocole {
	
	/***
	 * 
	 **/
	public void startCampaign();
	/***
	 * 
	 * @param campaignID
	 */
	public void pauseCampaign(long campaignID);
	/***
	 * 
	 * @param campaignID
	 */
	public void resumeCampaign(long campaignID);
	/***
	 * 
	 * @param campaignID
	 */
	public void stopCampaign(long campaignID);

	
}
