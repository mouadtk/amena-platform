package com.opm.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class indexController {


	@RequestMapping(value = "/index")
	public ModelAndView index() {
		return new ModelAndView("Hello OPM");
	}
	
	@RequestMapping(value = "/")
	public ModelAndView prepareCampaign(){
		
		return new ModelAndView("/campaign/prepare");
	}

}
