package com.opm.app.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opm.app.dao.IpDao;
import com.opm.app.model.server.ServerIP;

@Repository
public class IpDaoImpl implements IpDao{

	@Autowired
	SessionFactory factory;
	
	public boolean saveOrUpdate(ServerIP ip){
		
		try {
			factory.getCurrentSession().saveOrUpdate(ip);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}