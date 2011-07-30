package com.seismap.model.repository.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.seismap.model.entity.Category;
import com.seismap.model.entity.Map;
import com.seismap.model.entity.User;
import com.seismap.model.repository.MapRepository;

public class MapRepositoryImpl extends RepositoryImpl<Map, Long> implements
		MapRepository {

	public MapRepositoryImpl() {
		super(Map.class);
	}

	public Map getByCategoryAndName(Category category, String mapName) {
		String hql = "SELECT map FROM Map map WHERE map.name = :mapName AND map IN (SELECT category.maps FROM Category category WHERE category = :category)";
		return getByQuery(hql, parameter("mapName", mapName),
				parameter("category", category));
	}

	public Map getByUserAndName(User user, String mapName) {
		if (user.getMaps().isEmpty()) {
			return null;
		}
		DetachedCriteria criteria = getCriteria();
		criteria.add(Restrictions.eq("name", mapName));
		criteria.add(Restrictions.in("id", getIds(user.getMaps())));
		return getByCriteria(criteria, true);
	}
}
