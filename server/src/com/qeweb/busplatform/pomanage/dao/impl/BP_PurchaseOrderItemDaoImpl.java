package com.qeweb.busplatform.pomanage.dao.impl;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.busplatform.pomanage.dao.ia.IBP_PurchaseOrderItemDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.StringUtils;

/**
 *  订单明细dao Impl
 */
public class BP_PurchaseOrderItemDaoImpl extends BaseDaoHibImpl implements IBP_PurchaseOrderItemDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 346438618026659464L;

	/**
	 * 根据物料编码和物料名称查询采购订单ID
	 * @param materialCode
	 * @param materialName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getPOIds(String materialCode, String materialName) {
		if(StringUtils.isEmptyStr(materialCode) && StringUtils.isEmptyStr(materialName))
			return null;

		StringBuilder hql = new StringBuilder("select purchaseOrderBO.id from BP_PurchaseOrderItemBO where 1=1 ");
		List<Object> params = new LinkedList<Object>();
		if(StringUtils.isNotEmptyStr(materialCode)) {
			hql.append(" and material.materialCode like ? ");
			params.add(materialCode + "%");
		}
		if(StringUtils.isNotEmptyStr(materialName)) {
			hql.append(" and material.materialName like ? ");
			params.add(materialName + "%");
		}

		return findBySql(hql.toString(), params.toArray());
	}

	/**
	 * 根据订单ID查询订单对应的明细ID
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getPOItemIds(long orderId) {
		StringBuilder hql = new StringBuilder("select item.id from BP_PurchaseOrderItemBO item where ");
		hql.append("item.purchaseOrderBO.id = ? and ").append(IBaseDao.FIELD_DELETEFLAG).append(" = ").append(IBaseDao.UNDELETE_SIGNE);

		return findBySql(hql.toString(), new Object[]{orderId});
	}
}
