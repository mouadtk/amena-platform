/**
 * 
 */
package com.opm.app.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.opm.app.business.globaltest.MessageBuilder;
import com.opm.app.business.globaltest.TestResponse;
import com.opm.app.business.globaltest.TestResponseFactory;
import com.opm.app.business.globaltest.TestServcie;
import com.opm.app.business.globaltest.beans.MessageSettings;
import com.opm.app.business.globaltest.beans.TestSettings;
import com.opm.app.model.Server;
import com.opm.app.pmta.PmtaLogService;

/**
 * @author Mouad-tk
 *
 * 30 nov. 2016
 */
@Controller
@Scope("prototype")
@RequestMapping("/GlobTest")
public class GlobalTestController {
	
	@Autowired
	TestServcie    testService;
	@Autowired
	MessageBuilder messageBuilder;
	@Autowired
	PmtaLogService pmtaService;	
	@Autowired
	TestResponseFactory testResponseFactory;
	
	@RequestMapping(value = "/sendTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> index(HttpServletRequest request ) {
		
		Map<String, String>  rp =  new HashMap<String, String>();
		List<Server> servers = testService.getServersforTest(request);
		MessageSettings messageSettings = messageBuilder.msgSettingsFactory(request);
		TestSettings 	testSettings 	= testService.testSettingsFactory(request);
		
		String jobID =  testResponseFactory.keyGenrator();
		messageSettings.setJobID(jobID);		
		
		for(Server serv : servers){
			testService.sendMessage(serv,messageSettings,testSettings);
		}
		rp.put("JOB_ID", jobID);
		System.out.println(jobID);
		return rp;
	}	
	
	@RequestMapping(value = "/TestResults/{jobID}")
	@ResponseBody
	public List<TestResponse> sendTest(@PathVariable("jobID") String jobID) throws XMLStreamException, ParserConfigurationException, SAXException, IOException {
		
		Set<Server> servers = new HashSet<Server>();
		for(TestResponse aa : testResponseFactory.getTestHolder().get(jobID))
			if(aa.getResponseType()==null)
				servers.add(aa.getServer());

		for(Server serv : servers){

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			List<String> logs = pmtaService.logbByJobID(serv,jobID);
			for(String line : logs){
				try{
				InputSource is = new InputSource(new StringReader(line));
				Document doc = dBuilder.parse(is);
				String bounceType = doc.getDocumentElement().getNodeName();
				doc.getDocumentElement().getElementsByTagName("dsnStatus").item(0);
				String dsnStatus	= doc.getDocumentElement().getElementsByTagName("dsnStatus").item(0).getNodeValue();
				String dlvSourceIp	= doc.getDocumentElement().getElementsByTagName("dlvSourceIp").item(0).getNodeValue();
				String dsnDiag		= doc.getDocumentElement().getElementsByTagName("dsnDiag").item(0).getNodeValue();
				String rcpt			= doc.getDocumentElement().getElementsByTagName("rcpt").item(0).getNodeValue();
				String orig			= doc.getDocumentElement().getElementsByTagName("orig").item(0).getNodeValue();

				for(TestResponse aa : testResponseFactory.getTestHolder().get(jobID)){
					if(aa.getIpsrc().equalsIgnoreCase(dlvSourceIp.trim()) && 
						aa.getRcptto().equalsIgnoreCase(rcpt)){
						aa.setDsnDiag(dsnDiag);
						aa.setDsnStatus(dsnStatus);
						aa.setResponseType(bounceType);
					}
				}
				}catch(Exception e){
					System.out.println("start ");
					System.out.println(line);
					System.out.println("start ");
				}
			}
		}
		return testResponseFactory.getTestHolder().get(jobID);
	}
	
	@RequestMapping(value = "/CleanFactory/{jobID}")
	@ResponseBody
	public String removeFromTestFactory(@PathVariable("jobID") String jobID){
		testResponseFactory.removeItemFromHolder(jobID);
		System.out.println("SIZE ---- "+testResponseFactory.getTestHolder().size());
		return "OK";
	}
	
}