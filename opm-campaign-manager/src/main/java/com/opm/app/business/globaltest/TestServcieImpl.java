/**
 * 
 */
package com.opm.app.business.globaltest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opm.app.business.globaltest.beans.MessageSettings;
import com.opm.app.business.globaltest.beans.TestSettings;
import com.opm.app.model.Server;
import com.opm.app.model.ServerIP;

/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 ***/
@Service
@Scope("prototype")
public class TestServcieImpl  implements TestServcie{
	
	
//	public Map<String, String>  TestResults =  new HashMap<String, String>();
	@Autowired
	TestResponseFactory testResponseFactory;
	
	@Override
	public String sendMessage(Socket session, String mailfrom, String rcptto, String message) {
		
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
		/***
		 * return results 
		 **/
		return results;
	}

	public Socket connect(String mailHost, int SMTP_PORT){
		Socket session;
	    try{
	    	session = new Socket(mailHost, SMTP_PORT);
	        return session;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public List<Server> getServersforTest(HttpServletRequest request) {
		
		Map<String,Server> servs  = new HashMap<String, Server>(); 
		for(String line : Arrays.asList(request.getParameterValues("servers")) ){
			String[] a = line.split(",");
			ServerIP s = new ServerIP();
			s.setIp(a[1]);
			s.setDomain(a[2]);
			s.setServer(new Server(a[0]));
			if(servs.containsKey(a[0])){
				servs.get(a[0]).getIpserver().add(s);
			}else{
				Server ser = new Server(a[0]);
				ser.setMainip(a[1]);
				ser.getIpserver().add(s);
				servs.put(a[0], ser);
			}
		}		
		return new ArrayList<Server>(servs.values());
	}

	@Override
	public TestSettings testSettingsFactory(HttpServletRequest req) {
		
		TestSettings testSettings = new TestSettings();
		testSettings.setXdelay_email(req.getParameter("xdelay_email").isEmpty() ? 0 : Integer.parseInt(req.getParameter("xdelay_email")));
		testSettings.setNbr_email   (req.getParameter("nbr_email").isEmpty() ? 0 : Integer.parseInt(req.getParameter("nbr_email")));
		testSettings.setAll_at_once (req.getParameter("all_once") != null ? true : false);		
		return testSettings;		
	}


	@Override
	public void sendMessage(Server serv, MessageSettings messageSettings, TestSettings testSettings) {
		try{

			ServerIP mainip = serv.getIpserver().iterator().next();
			Socket session = connect(mainip.getIp(),25);
			int Index = 0;
			int sendSize= messageSettings.getSendTo().size();
			for(ServerIP ip : serv.getIpserver()){			
				
				String fromEmail = messageSettings.getFromMail().get(Index%messageSettings.getFromMail().size())+"@"+ip.getDomain();				
				while(Index%sendSize != sendSize-1 && testSettings.isAll_at_once()){		
					
					/// xJob 
					String rcptto= messageSettings.getSendTo().get(Index%sendSize);
					/**
					 * xdelay  per email  
					 **/
					if(testSettings.getXdelay_email() > 0 && testSettings.getNbr_email() >0  && Index != 0 && Index % testSettings.getNbr_email() == 0)
						try{Thread.sleep(testSettings.getXdelay_email());}catch(Exception e){}
					String Message = prepareMessage(messageSettings, Index, ip,serv.getName());
					/** resp obj */
					String testres  = sendMessage(session,fromEmail, rcptto, Message); 				
					testResponseFactory.addTest(messageSettings.getJobID(), serv , ip.getIp() , rcptto, fromEmail ,testres);
					Index++;
				}
				/**
				 * xdelay  per email
				 **/
				String rcptto= messageSettings.getSendTo().get(Index%sendSize);
				if(testSettings.getXdelay_email() > 0 && testSettings.getNbr_email() >0  &&  Index != 0 && Index % testSettings.getNbr_email() == 0) 
					try{Thread.sleep(testSettings.getXdelay_email());}catch(Exception e){}
				String Message = prepareMessage(messageSettings, Index,ip,serv.getName());
				String testres = sendMessage(session,fromEmail,rcptto, Message);
				testResponseFactory.addTest(messageSettings.getJobID(), serv, ip.getIp() , rcptto, fromEmail ,testres);
				Index++;
			}
			new PrintWriter(new OutputStreamWriter(session.getOutputStream()), true).println("quit");
			session.close();
		}catch(Exception e){
			testResponseFactory.addTest(messageSettings.getJobID(), serv, "" , "" , "" ,"can't establish connexion ");
		}

	}

	/**
	 * makan3rfolachi!
	 * @param messageSettings
	 * @param string 
	 * @param ip 
	 * @return
	 */
	private String prepareMessage(MessageSettings messageSettings, int index, ServerIP ip, String servername) {

		String msg =  messageSettings.getPreEmail();
		/**
		 * replace special tags
		 **/
		for(Tags  tag : Tags.values()){
			List<String> source = null;
			switch(tag.name()){
				case "__Recieved"	: 
					    source = messageSettings.getRecieved();
						msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "" :source.get(index%source.size()));
						break;
				case "__Reply_Name"	: 
						source = messageSettings.getReplyName();
						msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "reply" :source.get(index%source.size())+"@"+ip.getDomain());
						break;
				case "__Returnpath"	: 
						source = messageSettings.getReturnPath();
						msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "" :source.get(index%source.size()));
						break;
				case "__X_Mailer"	: 
						source = messageSettings.getxMailer();
						msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "" :source.get(index%source.size()));
						break;
				case "__Bounce_Name": 
						source = messageSettings.getBounceName();
						msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "" :source.get(index%source.size())+"@"+ip.getDomain());
						break;
				case "__To"			: 
					source = messageSettings.getSendTo();
					msg = msg.replaceAll(tag.name(),(source == null || source.isEmpty()) ? "" :source.get(index%source.size()));
					break;
				case "__From"		: 
					String fromname = messageSettings.getFromName().get(index%messageSettings.getFromName().size());
					String fromemail= messageSettings.getFromMail().get(index%messageSettings.getFromMail().size());
					msg = msg.replaceAll(tag.name(),fromname+" <"+fromemail+"@"+ip.getDomain()+">");
					break;
				case "__Date"		: 
					msg = msg.replaceAll(tag.name(),new Date()+"");
					break;
				case "__smtpDate"	: 
					msg = msg.replaceAll(tag.name(),"");
					break;
				case "__Ip"			: 
					msg = msg.replaceAll(tag.name(),ip.getIp());
					break;
				case "__From_dn"	: 
					msg = msg.replaceAll(tag.name(),ip.getDomain());
					break;					
				case "__VMTAIP"		: 
					msg = msg.replaceAll(tag.name(),ip.getIp());
					break;
				case "__X_Job": 
					msg = msg.replaceAll(tag.name(),messageSettings.getJobID());
					break;
				case "__Subject": 
					source = messageSettings.getSubject(); 
					String subj = (source == null || source.isEmpty()) ? "" :source.get(index%source.size());
					if(messageSettings.isAddip())
						subj+=" ["+ip.getIp()+"] ";
					if(messageSettings.isAddservername())
						subj+=" ["+servername+"] ";
					msg = msg.replaceAll(tag.name(),subj);
					break;	
				case "__Rand":{
						String pattern = "(__Rand\\(([0-9]+),([0-9]+)\\))";
					    Pattern r = Pattern.compile(pattern);
					    Matcher m = r.matcher(msg);
					    StringBuffer msgbuf = new StringBuffer();
					    while (m.find()) {			        	
				        	int Rand = Integer.parseInt(m.group(2))
				        			+ new Random().nextInt(Integer.parseInt(m.group(3)) - 
				        					Integer.parseInt(m.group(2)));			
				        	m.appendReplacement(msgbuf, Rand+"");
				        } 
					    msg = (msgbuf.length() > 0) ? msgbuf.toString() : msg;
					    }
 					break;
				case "__Char": {
						String pattern = "(__Char\\(([0-9]+)\\))";
					    Pattern r = Pattern.compile(pattern);
					    Matcher m = r.matcher(msg);
					    StringBuffer msgbuf = new StringBuffer();
					    while (m.find()) {			        					        				        
				        	m.appendReplacement(msgbuf, (char)Integer.parseInt(m.group(2))+"");
				        } 
					    msg = (msgbuf.length() > 0) ? msgbuf.toString() : msg;					    
					}
					break;
			}				
		}
		System.out.println("aprés "+msg);

		return msg;
	}

	
	public String setTags(String message , String tagName ,List<String> listOptions, int index){
		return message.replaceAll(tagName,(index < 0 || index > listOptions.size()) ?   listOptions.get(new Random().nextInt(listOptions.size())) : listOptions.get(index));
	} 
}
