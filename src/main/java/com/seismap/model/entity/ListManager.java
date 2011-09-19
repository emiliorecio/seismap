package com.seismap.model.entity;

import java.util.Collections;
import java.util.List;

public class ListManager<T> {

	private List<T> list;

	public ListManager(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return Collections.unmodifiableList(list);
	}

	public boolean moveTo(T element, int index) {
		int currentIndex = list.indexOf(element);
		if (currentIndex == -1) {
			return false;
		} else if (currentIndex == index) {
			// nothing to do.
		} else {
			list.remove(currentIndex);
			list.add(index, element);
		}
		return true;
	}

	public boolean add(T element) {
		return add(element, list.size());
	}

	public boolean add(T element, int index) {
		int currentIndex = list.indexOf(element);
		if (currentIndex != -1) {
			return false;
		}
		list.add(index, element);
		return true;
	}

	public boolean remove(T element) {
		boolean removed = list.remove(element);
		return removed;

	}
}
