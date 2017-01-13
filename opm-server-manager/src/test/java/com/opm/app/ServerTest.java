/**
 * 
 */
package com.opm.app;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.business.InstallService;
import com.opm.app.model.server.Server;
import com.opm.app.model.server.ServerIP;
import com.opm.app.service.DomainService;
import com.opm.app.service.ServerProviderService;
import com.opm.app.service.ServerService;
import com.opm.conf.AppConfig;

/**
 * @author Mouad-tk
 *
 * 5 janv. 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=AppConfig.class)
@Transactional(propagation = Propagation.REQUIRED)
public class ServerTest {

	@Autowired
	InstallService installservice;
	
	@Autowired
	DomainService domainService;
	
	@Autowired
	ServerService serverService;

	@Autowired
	 ServerProviderService providerService;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		Server server = new Server();
		server.setMainip("67.227.24.119");
		server.setPassword("Aq8@f1n@$");
		server.setPortssh(22);
		
		ServerIP ip1 = new ServerIP();
		ip1.setIp("67.227.24.119");
		ip1.setDomain("labopm.com");
		ip1.setServer(server);

		ServerIP ip2 = new ServerIP();
		ip2.setIp("192.168.9.244");
		ip2.setDomain("sub2.labopm.com");
		ip2.setServer(server);

		ServerIP ip3 = new ServerIP();
		ip3.setIp("192.168.9.252");
		ip3.setDomain("sub3.labopm.com");
		ip3.setServer(server);
		Set<ServerIP> ips = new HashSet<>();
		ips.add(ip1);
		ips.add(ip2);
		ips.add(ip3);
		server.setIpserver(ips);
		serverService.saveOrUpdate(server);
		for (ServerIP ip :  server.getIpserver()) {
			System.out.println(ip.getIp());
			assert(ip.getId()>0);	
		}		
	}

}
