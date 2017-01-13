/**
 * 
 */
package com.opm.app.business.campaign.beans;

/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 */
public class SendSettings {
	
	int xdelay_email;
	int nbr_email;
	
	boolean all_at_once;

	public int getXdelay_email() {
		return xdelay_email;
	}

	public void setXdelay_email(int xdelay_email) {
		this.xdelay_email = xdelay_email;
	}

	public int getNbr_email() {
		return nbr_email;
	}

	public void setNbr_email(int nbr_email) {
		this.nbr_email = nbr_email;
	}

	public boolean isAll_at_once() {
		return all_at_once;
	}

	public void setAll_at_once(boolean all_at_once) {
		this.all_at_once = all_at_once;
	}
	
	@Override
	public String toString() {
		return "SendSettings [xdelay_email=" + xdelay_email + ", nbr_email=" + nbr_email + ", all_at_once="
				+ all_at_once + "]";
	}
}
