package com.opm.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opm.app.dao.DomainDao;
import com.opm.app.model.enumeration.DomainState;
import com.opm.app.model.server.Domain;
import com.opm.app.model.server.DomainProvider;
import com.opm.app.service.DomainProviderService;
import com.opm.app.service.DomainService;
@Transactional
@Service
public class DomainServiceImpl implements DomainService{

	@Autowired
	DomainProviderService domainProviderService;
	
	@Autowired
	private DomainDao dao;
	
	
	@Override
	public List<Domain> getFreeDomains(int nbr, int oo) {
		
		List<DomainProvider> providers = domainProviderService.getAllProviders();
		List<Domain> domains;
		for (DomainProvider provider : providers) {
			domains= dao.getDomainsGoupByProvider(nbr, provider.getId());
			if(domains!=null && domains.size()==nbr){
				System.out.println("DOMAINS FOUND");
				try {
					for (Domain domain : domains) {
						domain.setState(DomainState.Reserved);
						domain.setUpdatedAt(new Date());
						dao.saveOrUpdate(domain);
					}
					return domains;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("manque de domains");
			}
		}
		return null;
	}

	@Override
	public boolean saveOrUpdate(Domain domain) {
		return dao.saveOrUpdate(domain);
	}
	
	
}
