package com.opm.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.DomainProviderDao;
import com.opm.app.model.server.DomainProvider;

@Repository
public class DomainProviderDaoImpl implements DomainProviderDao{

	@Autowired
	SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DomainProvider> getAllProviders() {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(DomainProvider.class);
			criteria.add(Restrictions.eq("actif", true));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}