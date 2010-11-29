package com.seismap.model.repository.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.seismap.model.entity.Agency;
import com.seismap.model.repository.AgencyRepository;

public class AgencyRepositoryImpl extends HibernateDaoSupport implements
		AgencyRepository {

	public void put(Agency agency) {
		this.getHibernateTemplate().save(agency);

	}

	public Agency getByCode(String code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Agency.class);
		criteria.add(Restrictions.eq("code", code));
		List<?> list = getHibernateTemplate().findByCriteria(criteria);
		if (list.isEmpty()) {
			return null;
		}
		return (Agency) list.get(0);
	}

}
