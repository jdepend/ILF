package com.qeweb.demo.interaction.relation.bo;

import com.qeweb.demo.common.bo.DemoPOItemBO;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.utils.StringUtils;

public class DemoConRealation_TargetBO_3 extends DemoPOItemBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9096547105446679040L;

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(StringUtils.isEqual("demoConRelationBO3", bot.getBoName())) {
			return queryByPO(bot, start);
		}
		else {
			return super.query(bot, start);
		}
	}
	
	/**
	 * 根据订单信息查询订单明细
	 * @param bot
	 * @param start
	 * @return
	 * @throws Exception
	 */
	public Object queryByPO(BOTemplate bot, int start) throws Exception {
		Object poId = bot.getValue("id");
		bot.getBotMap().clear();
		if(poId != null)
			bot.push("purchaseOrderBO.id", poId);
		else
			bot.push("purchaseOrderBO.id", -1L);
		
		return super.query(bot, start);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPOItemBO.class;
	}
}
