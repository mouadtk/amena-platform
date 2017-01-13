package com.opm.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.InstallationLogDao;
import com.opm.app.model.server.InstallationLog;

@Repository
public class InstallationLogDaoImpl implements InstallationLogDao{

	@Autowired
	SessionFactory factory;

	@Override
	public boolean saveOrupdate(InstallationLog installationLog) {
		try {
			factory.getCurrentSession().saveOrUpdate(installationLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstallationLog> getAllLog() {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(InstallationLog.class);
			criteria.add(Restrictions.eq("actif", true));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstallationLog> getAllLogByServer(long id) {
		try {
			Criteria criteria= factory.getCurrentSession().createCriteria(InstallationLog.class);
			criteria.add(Restrictions.eq("actif", true));
			criteria.createAlias("server", "s");
			criteria.add(Restrictions.eq("s.id", id));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}