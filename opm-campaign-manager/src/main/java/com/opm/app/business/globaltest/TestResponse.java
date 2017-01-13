/**
 * 
 */
package com.opm.app.business.globaltest;

import com.opm.app.model.Server;

/**
 * @author Mouad-tk
 *
 * 20 déc. 2016
 */
public class TestResponse {

	Server server;
	String rcptto;
	String from;
	String ipsrc;
	boolean received;
	String socketMsg;
	
	String responseType; 		
	String dsnStatus;
	private String dsnDiag;
	
	public void parResults(String line){
		/** kt3ma blm7chi */
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getRcptto() {
		return rcptto;
	}

	public void setRcptto(String rcptto) {
		this.rcptto = rcptto;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getIpsrc() {
		return ipsrc;
	}

	public void setIpsrc(String ipsrc) {
		this.ipsrc = ipsrc;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getSocketMsg() {
		return socketMsg;
	}

	public void setSocketMsg(String socketMsg) {
		this.socketMsg = socketMsg;
	}

	public String getDsnStatus() {
		return dsnStatus;
	}

	public void setDsnStatus(String dsnStatus) {
		this.dsnStatus = dsnStatus;
	}

	public String getDsnDiag() {
		return dsnDiag;
	}

	public void setDsnDiag(String dsnDiag) {
		this.dsnDiag = dsnDiag;
	}

	@Override
	public String toString() {
		return "TestResponse [serverName=" + server.getName() + ", rcptto=" + rcptto + ", from=" + from + ", ipsrc=" + ipsrc
				+ ", received=" + received + ", socketMsg=" + socketMsg + ", responseType=" + responseType
				+ ", dsnStatus=" + dsnStatus + "]";
	}
	
}
