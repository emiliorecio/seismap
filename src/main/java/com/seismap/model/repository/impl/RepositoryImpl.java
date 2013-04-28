package com.seismap.model.repository.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.seismap.model.entity.Identifiable;
import com.seismap.model.repository.Repository;

public abstract class RepositoryImpl<T, K extends Serializable> extends
		HibernateDaoSupport implements Repository<T, K> {

	protected static class FieldValue {
		private String name;
		private Object value;

		public FieldValue(String name, Object value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return name + "=" + value;
		}
	}

	protected static class ParameterValue {
		private String name;
		private Object value;

		public ParameterValue(String name, Object value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return name + "=" + value;
		}
	}

	protected FieldValue field(String name, Object value) {
		return new FieldValue(name, value);
	}

	protected ParameterValue parameter(String name, Object value) {
		return new ParameterValue(name, value);
	}

	private Class<T> theClass;

	public RepositoryImpl(Class<T> theClass) {
		this.theClass = theClass;
	}

	public T get(K id) {
		Object object = getHibernateTemplate().get(theClass, id);
		T casted = theClass.cast(object);
		return casted;
	}

	public T fetch(K id) {
		T element = get(id);
		if (element == null) {
			throw new IllegalArgumentException("No " + theClass.getSimpleName()
					+ " with id " + id);
		}
		return element;
	};

	protected T getByValue(String fieldName, Object fieldValue) {
		return getByValues(field(fieldName, fieldValue));
	}

	protected T getByValues(FieldValue... fieldValues) {
		List<T> list = getListByValues(fieldValues);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			Object element = list.get(0);
			return theClass.cast(element);
		} else {
			throw new IllegalArgumentException(list.size() + " "
					+ theClass.getSimpleName() + " with "
					+ Arrays.toString(fieldValues));
		}
	}

	protected List<T> getListByValue(String fieldName, Object fieldValue) {
		return getListByValues(field(fieldName, fieldValue));
	}

	protected List<T> getListByValues(FieldValue... fieldValues) {
		DetachedCriteria criteria = DetachedCriteria.forClass(theClass);
		Set<String> aliases = new HashSet<String>();
		for (FieldValue fieldValue : fieldValues) {
			String name = fieldValue.getName();
			int pathEndIndex = name.lastIndexOf('.');
			String nameAlias;
			if (pathEndIndex == -1) {
				nameAlias = name;
			} else {
				String path = name.substring(0, pathEndIndex);
				String pathAlias = path.replace('.', '_');
				nameAlias = pathAlias + name.substring(pathEndIndex);
				if (aliases.add(path)) {
					criteria.createAlias(path, pathAlias);
				}
			}
			Object value = fieldValue.getValue();
			criteria.add(Restrictions.eq(nameAlias, value));
		}
		List<?> list = getHibernateTemplate().findByCriteria(criteria);
		return castList(list);
	}

	protected T fetchByValue(String fieldName, Object fieldValue) {
		return fetchByValues(field(fieldName, fieldValue));
	}

	protected T fetchByValues(FieldValue... fieldValues) {
		T element = getByValues(fieldValues);
		if (element == null) {
			throw new IllegalArgumentException("No " + theClass.getSimpleName()
					+ " with " + Arrays.toString(fieldValues));
		}
		return element;
	}

	@SuppressWarnings("unchecked")
	protected List<T> castList(List<?> list) {
		return (List<T>) list;
	}

	public void put(T object) {
		getHibernateTemplate().save(object);
	}

	protected DetachedCriteria getCriteria() {
		return DetachedCriteria.forClass(this.theClass);
	}

	protected List<T> getListByCriteria(final DetachedCriteria criteria,
			boolean distinctRoot) {
		if (distinctRoot) {
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		List<?> list = this.getHibernateTemplate().findByCriteria(criteria);
		return castList(list);
	}

	protected T getByCriteria(final DetachedCriteria criteria,
			boolean distinctRoot) {
		List<T> list = getListByCriteria(criteria, distinctRoot);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() >= 1) {
			Object element = list.get(0);
			return theClass.cast(element);
		} else {
			throw new IllegalArgumentException(list.size() + " "
					+ theClass.getSimpleName() + " with criteria: " + criteria);
		}
	}

	protected T fetchByCriteria(final DetachedCriteria criteria,
			boolean distinctRoot) {
		T element = getByCriteria(criteria, distinctRoot);
		if (element == null) {
			throw new IllegalArgumentException("No " + theClass.getSimpleName()
					+ " with criteria: " + criteria);
		}
		return element;
	}

	public List<T> getAll() {
		List<?> list = this.getHibernateTemplate().loadAll(this.theClass);
		return castList(list);
	}

	protected List<T> getListByQuery(final String hql,
			ParameterValue... parameters) {
		String[] parameterNames = new String[parameters.length];
		Object[] parameterValues = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterNames[i] = parameters[i].getName();
			parameterValues[i] = parameters[i].getValue();
		}
		List<?> list = this.getHibernateTemplate().findByNamedParam(hql,
				parameterNames, parameterValues);
		return castList(list);
	}

	protected T getByQuery(final String hql, ParameterValue... parameters) {
		List<T> list = getListByQuery(hql, parameters);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() >= 1) {
			Object element = list.get(0);
			return theClass.cast(element);
		} else {
			throw new IllegalArgumentException(list.size() + " "
					+ theClass.getSimpleName() + " with query: " + hql);
		}
	}

	protected T fetchByQuery(final String hql, ParameterValue... parameters) {
		T element = getByQuery(hql, parameters);
		if (element == null) {
			throw new IllegalArgumentException("No " + theClass.getSimpleName()
					+ " with query: " + hql);
		}
		return element;
	}

	@SuppressWarnings("unchecked")
	protected <I extends Serializable> I[] getIds(
			Collection<? extends Identifiable<I>> objects) {
		I[] ids = (I[]) new Serializable[objects.size()];
		int index = 0;
		for (Identifiable<I> object : objects) {
			ids[index++] = object.getId();
		}
		return ids;
	}

	public List<T> list() {
		List<?> list = getHibernateTemplate().loadAll(theClass);
		return castList(list);
	}

	public T getSingleton() {
		List<T> list = list();
		if (list.isEmpty()) {
			return null;
		} else if (list.size() >= 1) {
			Object element = list.get(0);
			return theClass.cast(element);
		} else {
			throw new IllegalArgumentException(list.size() + " "
					+ theClass.getSimpleName() + " as singleton.");
		}
	}

	public T fetchSingleton() {
		T element = getSingleton();
		if (element == null) {
			throw new IllegalArgumentException("No " + theClass.getSimpleName()
					+ " as singleton");
		}
		return element;
	}
	
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}
}
