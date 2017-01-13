package com.opm.app.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.ServerDao;
import com.opm.app.model.enumeration.InstallationStatus;
import com.opm.app.model.server.Server;

@Repository("serverDao")
public class ServerDaoImpl implements ServerDao{

	@Autowired
	private SessionFactory factory;

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	public boolean saveOrUpdate(Server server) {
		try {
			factory.getCurrentSession().saveOrUpdate(server);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Server findById(long id, boolean lazyMode) {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(Server.class);
			criteria.add(Restrictions.eq("actif", true));
			criteria.add(Restrictions.eq("id", id));
			Server server= (Server) criteria.uniqueResult();
			if(server!=null && !lazyMode)
				Hibernate.initialize(server.getIpserver());
			return server;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Server> getALLServers() {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(Server.class);
			criteria.add(Restrictions.eq("actif", true));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Server findByMainIp(String mainIp) {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(Server.class);
			criteria.add(Restrictions.eq("actif", true));
			criteria.add(Restrictions.eq("mainip", mainIp));
			return (Server) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Server> getALLServers(List<Long> ids) {
		try {
			Query query= factory.getCurrentSession().createQuery("from Server where id in :ids");
			query.setParameterList("ids", ids);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Server> getNewServerOrFailedInstalltion() {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(Server.class);
			criteria.add(Restrictions.eq("actif", true));
			criteria.add(
				Restrictions.or(Restrictions.eq("installState", InstallationStatus.WAITING),
				Restrictions.eq("installState", InstallationStatus.FAILED)
			));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}