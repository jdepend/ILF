package com.qeweb.framework.base;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class QewebDetachedCriteria extends DetachedCriteria {

	private static final long serialVersionUID = -1004911633424109612L;
	
	private ArrayList<Order> orders = new ArrayList<Order>();

	/**
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static QewebDetachedCriteria forClass(Class clazz) {
		return new QewebDetachedCriteria(clazz);
	}
	
	/**
	 * @param entityClass
	 */
	@SuppressWarnings("rawtypes")
	public QewebDetachedCriteria(Class entityClass) {
		super(entityClass.getName());
	}

	/**
	 * @see org.hibernate.criterion.DetachedCriteria#addOrder(org.hibernate.criterion.O­rder)
	 */
	public DetachedCriteria addOrder(Order order) {
		orders.add(order);
		return this;
	}

	/**
	 * 填充入排序信息设置
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillOrdersSetting(Criteria criteria) {
		for (Order order : orders) {
			criteria.addOrder(order);
		}

		return criteria;
	}

}
