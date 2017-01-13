/**
 * 
 */
package com.opm.app.business.campaign.classic.send;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.opm.app.business.campaign.CoreSend;

/**
 * @author Mouad-tk
 *
 * 3 janv. 2017
 */
public class SendCoreImpl implements CoreSend{

	/***
	 * 
	 */
	@Override
	public String sendMessage(Socket session, String mailfrom, String rcptto, String message) throws Exception {
		
		String results = "";
	    InputStream inn;
	    OutputStream outt;
	    BufferedReader msg;
	    InputStream msgInputStream;
	    BufferedReader in ;
	    PrintWriter out;
	    try{
	    	  msgInputStream = new ByteArrayInputStream(message.getBytes());
	    	  msg = new BufferedReader(new InputStreamReader(msgInputStream));
	    	  if (session == null){
		          System.err.println("socket is null.");
		          results = "error : socket is null.";
		    	  return results;	    
	    	  }
	    	  
	    	  if (!session.isConnected()) {
	    		  System.out.println("socket is disconnected.");
	    		  results = "error : Failed to open streams to socket.";
	    		  return results;
	    	  }

	    	  inn = session.getInputStream();
		      outt = session.getOutputStream();
		      in = new BufferedReader(new InputStreamReader(inn));
		      out = new PrintWriter(new OutputStreamWriter(outt), true);
		      if (inn == null || outt == null) {
		          System.err.println("Failed to open streams to socket.");
		          results = "error : Failed to open streams to socket.";
		    	  return results;
		      }
		     /**
			  * send message 
			  **/
		      System.err.println("MAIL From:" + mailfrom + "");
		      out.println("MAIL From:" + mailfrom + "");
		      String senderOK = in.readLine();
		      System.err.println(senderOK);
		      
		      System.err.println("RCPT TO:" + rcptto + "");
		      out.println("RCPT TO:" + rcptto);
		      String recipientOK = in.readLine();
		      System.err.println(recipientOK);
		      
		      System.err.println("DATA");
		      out.println("DATA");
		      String line;
		      while ((line = msg.readLine()) != null) {
		        out.println(line);
		        System.err.println(line);
		      }
		      System.err.println(".");
		      out.println(".");
		      String acceptedOK = in.readLine();
		      results = acceptedOK;
		      System.err.println(acceptedOK);
		      
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return results;
	}

	/***
	 * 
	 */
	@Override
	public Socket connect(String mailHost, int smtpPort, int timeOut) throws Exception {

		Socket session;
	    try{
	    	session = new Socket();
	    	session.connect(new InetSocketAddress(mailHost, smtpPort), timeOut);
	    	return session;
	    }catch(Exception e){
	    	/**
	    	 * do something here
	    	 **/
	    	e.printStackTrace();
	    	throw e;
	    }	    
	
	}

}
