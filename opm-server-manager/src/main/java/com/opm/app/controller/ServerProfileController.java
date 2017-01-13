/**
 * 
 */
package com.opm.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.opm.app.business.Statistics;
import com.opm.app.business.SystemConfig;
import com.opm.app.model.server.Server;
import com.opm.app.service.ServerService;

/**
 * @author Mouad-tk
 *
 * 5 janv. 2017
 */

@Controller
@RequestMapping("/Monitor")
public class ServerProfileController {

	@Autowired
	ServerService serverService;
	
	@Autowired(required=  false)
	Statistics serverStatistics;
	
	@Autowired(required=  false)
	SystemConfig sysConfig;

	/***
	 * @param idServer
	 * @return main server informations
	 * 	- domains, 
	 * 	- ips 
	 *  - server provider
	 *  - domain provider
	 ***/
	@RequestMapping("/Profile/{idServer}")
	public ModelAndView index(@PathVariable() int idServer){

		Server server = serverService.getCompleteServerInfos(idServer);
		ModelAndView mv = new ModelAndView("/server/monitor/server.profile");
		mv.addObject("server", server);
		return mv;
	}
	
	/***
	 * statistics between two dates
	 * @params fromDate, toDate  
	 * default value
	 * 		- from  : one month back
	 * 		- to 	: today
	 * @return statistics of :
	 *  - earning 
	 *  - clicks
	 *  - sent
	 **/	
	@RequestMapping("/statistics")
	public ModelAndView statistics(@RequestParam("idServer") int idServer,@RequestParam("from") Date from,@RequestParam("to") Date to){
		
		ModelAndView mv = new ModelAndView("/server/monitor/server.statistics");
		serverStatistics.getTotalClick(idServer, from, to);
		serverStatistics.getTotalEarning(idServer, from, to);
		serverStatistics.getTotalSent(idServer, from, to);
		return mv;
	}
	
	/***
	 * @param idServer
	 * @return historique des attributions de serveur, & ISP 
	 * 	- isp
	 *  - teams
	 *  - members(Mailer, TL)
	 **/
	@RequestMapping("/attribution")
	public ModelAndView serverAttribution(@RequestParam("idServer") int idServer){
		
		ModelAndView mv = new ModelAndView("/server/monitor/server.attribution");
		
		return mv;
	}

	/***
	 * @param idServer
	 * @return system state
	 * 	- informations about : µCPU, Memory, Disc, Bandwidth...
	 *  - installed services, state.
	 **/
	@RequestMapping("/systemInfo")
	public ModelAndView serverSystemState(@RequestParam("idServer") int idServer){
		
		ModelAndView mv = new ModelAndView("/server/monitor/server.system");
		Server serv = serverService.findById(idServer, true);
		sysConfig.systemState(serv);
		sysConfig.serviceStates(serv);
		return mv;
	
	}

	
	
		
}
