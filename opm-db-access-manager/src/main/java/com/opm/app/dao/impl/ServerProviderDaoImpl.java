package com.opm.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.ServerProviderDao;
import com.opm.app.model.server.ServerProvider;

@Repository("serverProviderDao")
public class ServerProviderDaoImpl implements ServerProviderDao {

	@Autowired
	private SessionFactory factory;

	@Override
	public ServerProvider findById(long id) {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(ServerProvider.class);
			criteria.add(Restrictions.eq("id", id));
			return (ServerProvider) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ServerProvider findName(String name) {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(ServerProvider.class);
			criteria.add(Restrictions.eq("name", name));
			return (ServerProvider) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServerProvider> getAllProviders() {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(ServerProvider.class);
			criteria.add(Restrictions.eq("actif", true));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}