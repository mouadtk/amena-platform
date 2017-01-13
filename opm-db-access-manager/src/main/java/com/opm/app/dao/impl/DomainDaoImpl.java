package com.opm.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.DomainDao;
import com.opm.app.model.enumeration.DomainState;
import com.opm.app.model.server.Domain;
import com.opm.app.model.server.DomainProvider;

@Repository("domainDao")
public class DomainDaoImpl implements DomainDao{

	@Autowired
	private SessionFactory factory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Domain> getFreeDomains(int nbr) {
		try {
			Criteria criteria=factory.getCurrentSession().createCriteria(Domain.class);
			criteria.add(Restrictions.eq("actif", true));
			criteria.add(Restrictions.eq("enable", true));
			criteria.addOrder(Order.asc("dateExpiration"));
			criteria.setMaxResults(nbr);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveOrUpdate(Domain domain) {
		try {
			factory.getCurrentSession().saveOrUpdate(domain);
			System.out.println("Domain updated");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domain> getDomainsGoupByProvider(int nbr, long idProvider) {
		try {
			Query query= factory.getCurrentSession().createQuery("FROM Domain d WHERE state=:state and d.provider.id=:id ");
			query.setLong("id", idProvider);
			query.setParameter("state", DomainState.New);
			query.setMaxResults(nbr);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/****
	 * not completed
	 */
	@Override
	public DomainProvider getProvider() {
		Query query= factory.getCurrentSession().createQuery("FROM Domain d WHERE state=:state and d.provider.id=:id ");
		query.setLong("id", 0);
		return null;
	}

}