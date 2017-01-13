/**
 * 
 */
package com.opm.app.business.implementation;

import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Mouad-tk
 *
 * 28 nov. 2016
 */
@Aspect
@Component
public class InstallLogger {
	
	@AfterReturning (pointcut="execution(java.util.Map<*, *> com.opm.app.business.InstallService.copyInstallFiles(*,*)) || "
			+ "execution(java.util.Map<*, *> com.opm.app.business.InstallService.execCommand(*,*))", returning = "_logger")
	public void logAfter(java.util.Map<String, String> _logger) throws Exception {
		System.out.println("logger ");
		Map<String, String> logger = (Map<String, String>) _logger;
		if(logger.size()>0)
			throw new Exception();
		else
			System.err.println("service installed successfully!");
	}
	
}
