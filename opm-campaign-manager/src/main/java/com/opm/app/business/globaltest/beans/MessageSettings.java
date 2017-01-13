/**
 * 
 */
package com.opm.app.business.globaltest.beans;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 */
public class MessageSettings {
	
	String preEmail;
	List<String> sendTo;
	List<String> fromName;
	List<String> fromMail;
	List<String> bounceName;
	List<String> replyName;
	List<String> returnPath;
	List<String> recieved;
	List<String> xMailer;
	List<String> subject;
	String jobID;
	boolean addip;
	boolean addservername;
	/**
	 * 
	 */
	public MessageSettings() {
		
	}

	public String getPreEmail() {
		return preEmail;
	}

	public void setPreEmail(String preEmail) {
		this.preEmail = preEmail;
	}

	public List<String> getSendTo() {
		return sendTo;
	}

	public void setSendTo(List<String> sendTo) {
		this.sendTo = sendTo;
	}

	public List<String> getFromName() {
		return fromName;
	}

	public void setFromName(List<String> fromName) {
		this.fromName = fromName;
	}

	public List<String> getFromMail() {
		return fromMail;
	}

	public void setFromMail(List<String> fromMail) {
		this.fromMail = fromMail;
	}

	public List<String> getBounceName() {
		return bounceName;
	}

	public void setBounceName(List<String> bounceName) {
		this.bounceName = bounceName;
	}

	public List<String> getReplyName() {
		return replyName;
	}

	public void setReplyName(List<String> replyName) {
		this.replyName = replyName;
	}

	public List<String> getReturnPath() {
		return returnPath;
	}

	public void setReturnPath(List<String> returnPath) {
		this.returnPath = returnPath;
	}

	public List<String> getRecieved() {
		return recieved;
	}

	public void setRecieved(List<String> recieved) {
		this.recieved = recieved;
	}

	public List<String> getxMailer() {
		return xMailer;
	}

	public void setxMailer(List<String> xMail) {
		this.xMailer = xMail;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public boolean isAddip() {
		return addip;
	}

	public void setAddip(boolean addip) {
		this.addip = addip;
	}

	public boolean isAddservername() {
		return addservername;
	}

	public void setAddservername(boolean addservername) {
		this.addservername = addservername;
	}

	@Override
	public String toString() {
		return "MessageSettings [preEmail=" + preEmail + ", sendTo=" + sendTo + ", fromName=" + fromName + ", fromMail="
				+ fromMail + ", bounceName=" + bounceName + ", replyName=" + replyName + ", returnPath=" + returnPath
				+ ", recieved=" + recieved + ", xMailer=" + xMailer + "]";
	}
	
}
