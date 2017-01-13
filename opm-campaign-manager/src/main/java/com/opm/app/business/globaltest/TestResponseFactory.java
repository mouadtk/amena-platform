package com.opm.app.business.globaltest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opm.app.model.Server;
import com.opm.app.pmta.PmtaLogService;

/***
 * @author Mouad-tk
 *
 * 19 déc. 2016
 **/
@Component
public class TestResponseFactory {
	
	/***
	 * Key 		: xJob (job id)
	 * Value 	: List<TestResponse> 
	 * 
	 **/
	private Map<String, List<TestResponse>> TestHolder = new HashMap<String ,List<TestResponse>>();
	
	
	@Autowired
	PmtaLogService pmtaLogService;
	
	public Map<String, List<TestResponse>> getTestHolder() {
		return TestHolder;
	}

	public void setTestHolder(String jobID, List<TestResponse> testResp) {		
		TestHolder.put(jobID,testResp);
	}
	
	public String keyGenrator(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
		Random r = new Random();
		return ""+format.format(new Date())+"-"+r.nextInt(300);
	}

	boolean getTestResponse(String jobId ){
		return TestHolder.containsKey(jobId);
	}

	/**
	 * @param jobID
	 * @param name
	 * @param ip
	 * @param rcptto
	 * @param fromEmail
	 * @param testres
	 * @param  
	 */
	public void addTest(String jobID, Server server, String ip, String rcptto, String fromEmail, String testres) {
		List<TestResponse> ListResp;
		TestResponse testResp = new TestResponse();
		testResp.setServer(server);
		testResp.setIpsrc(ip);
		testResp.setRcptto(rcptto);
		testResp.setFrom(fromEmail);
		testResp.setSocketMsg(testres);
		testResp.setReceived(testres.contains("250"));
		if(!TestHolder.containsKey(jobID)){
			ListResp = new ArrayList<TestResponse>();
			ListResp.add(testResp);
			TestHolder.put(jobID,ListResp);
		}else{
			TestHolder.get(jobID).add(testResp);
		}
			
	}

	public void removeItemFromHolder(String jobId) {
		System.out.println("JOB "+jobId);
		System.out.println(TestHolder.keySet());;
		if(!this.TestHolder.containsKey(jobId)){
			System.out.println("KEY NOT FOUND");
			return ;
		}
		System.out.println("REMOVE ITEM "+jobId);
		this.TestHolder.put(jobId, null);
		this.TestHolder.remove(jobId);
	}
	
	
	
}