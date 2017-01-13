package com.opm.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.opm.conf.CampaignmanagerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CampaignmanagerApplication.class)
@WebAppConfiguration
public class CampaignmanagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
