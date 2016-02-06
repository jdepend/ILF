package com.qeweb.busplatform.pomanage.task;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO;


/**
 * 订单执行情况统计任务
 */
public class BP_PurchaseOrderStatisticsTimer extends TimerTask {

	private final Log log = LogFactory.getLog(BP_PurchaseOrderStatisticsTimer.class);

	@Override
	public void run() {
		log.info("统计订单执行数据开始....");
		try {
			new BP_PurchaseOrderExecutionBO().statistics();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getCause(), e);
		}
		log.info("统计订单执行数据结束....");
	}
}
