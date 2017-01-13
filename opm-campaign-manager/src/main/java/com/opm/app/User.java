/**
 * 
 */
package com.opm.app;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author Mouad-tk
 *
 * 2 janv. 2017
 * 
 * hada m3reft f ina package ghaynzel !!!! 
 * fre3 lkar!!
 **/
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

	long id;
	String username;
	String role;
	
		
}
