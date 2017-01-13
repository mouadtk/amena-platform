/**
 * 
 */
package com.opm.app.business.globaltest;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.opm.app.business.globaltest.beans.MessageSettings;


/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 */
@Service
public class MessageBuilderTest implements MessageBuilder {
		
	
	/***
	 * Message with tags to replace  
	 **/
	@Override
	public String messageBuild(HttpServletRequest data){
		
		/**
		 * vmta-test
		 * x-job : tracking results
		 * Build raw message 
		 */
		String headers = "X-Virtual-mta : virtual-mta-"+Tags.__VMTAIP+"\n";
		headers += "X-job:" +Tags.__X_Job+"\n";
		
		String mail = headers + data.getParameter("formatheader")+" \n \n "+data.getParameter("message");
		return mail;
	}	
	
	@Override
	public MessageSettings msgSettingsFactory(HttpServletRequest req){
		
		MessageSettings msgSettings =  new MessageSettings();
		/**
		 * vmta-test
		 * x-job : tracking results
		 * Build raw message 
		 */
		String headers = "X-Virtual-mta : Test-MTA-"+Tags.__VMTAIP+"\n";
		headers += "X-job:" +Tags.__X_Job+"\n";
		msgSettings.setPreEmail(headers + req.getParameter("formatheader")+" \n \n "+req.getParameter("message"));
		/***
		 * header values options 
		 */ 
		msgSettings.setSendTo(Arrays.asList(req.getParameter("sendto").split(",")));
		msgSettings.setFromName(Arrays.asList(	req.getParameter("fromname").split(",")));
		msgSettings.setFromMail(Arrays.asList(	req.getParameter("fromemail").split(",")));
		msgSettings.setBounceName(Arrays.asList(req.getParameter("namebounce").split(",")));
		msgSettings.setReplyName(Arrays.asList( req.getParameter("replyname").split(",")));
		msgSettings.setReturnPath(Arrays.asList(req.getParameter("returnpath").split(",")));
		msgSettings.setRecieved(Arrays.asList(	req.getParameter("received").split(",")));
		msgSettings.setxMailer(Arrays.asList(	req.getParameter("xmailer").split(",")));
		msgSettings.setSubject(Arrays.asList(	req.getParameter("subject").split(",")));
		msgSettings.setAddip((req.getParameter("addip")!=null? true :  false));
		msgSettings.setAddservername((req.getParameter("addservname")!=null? true :  false));
		return msgSettings;
	}
	
}
