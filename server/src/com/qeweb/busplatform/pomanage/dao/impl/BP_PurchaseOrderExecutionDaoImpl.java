package com.qeweb.busplatform.pomanage.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderStatisticsBO;
import com.qeweb.busplatform.pomanage.dao.ia.IBP_PurchaseOrderExecutionDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 *  订单执行dao Impl
 */
public class BP_PurchaseOrderExecutionDaoImpl extends BaseDaoHibImpl implements IBP_PurchaseOrderExecutionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3551334022754756573L;

	/**
	 * 获取需统计的订单明细
	 * @return [订单行ID, 订单数量, 确认状态, 关闭状态]
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPendingStatisticsOrderItems() {
		StringBuilder sql = new StringBuilder("");
		sql.append(" SELECT ITEM.ID, ITEM.ORDER_QTY, ITEM.CONFIRM_STATUS, ITEM.CLOSE_STATUS FROM QEWEB_BP_PUR_ORDER_ITEM ITEM");
		sql.append(" LEFT JOIN QEWEB_BP_PUR_ORDER_STATISTICS STATIS ON STATIS.PURCHASE_ITEM_ID = ITEM.ID");
		sql.append(" WHERE (STATIS.IS_STATISTICS = 1 OR STATIS.IS_STATISTICS IS NULL)");

		List<Object[]> result = this.createQuery(sql.toString());

		return result;
	}

	/**
	 * 根据订单行获取统计
	 * @param itemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BP_PurchaseOrderStatisticsBO getOrderStatisticsByItmeId(long itemId) {
		StringBuilder hql = new StringBuilder("from BP_PurchaseOrderStatisticsBO statistics where")
		.append(" statistics.purchaseOrderItemId = ? ");

		List<Object> params = new LinkedList<Object>();
		params.add(itemId);

		List<BP_PurchaseOrderStatisticsBO> result = this.findBySql(hql.toString(), params.toArray());

		return ContainerUtil.isNull(result) ? null : result.get(0);
	}

	@Override
	public void flush() {
		super.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.flush();
				session.clear();
				return null;
			}
		});
	}
}
