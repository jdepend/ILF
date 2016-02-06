package com.qeweb.framework.impconfig.mdt.use.dao.ia;

import java.util.List;

import com.qeweb.framework.impconfig.mdt.use.bean.MDT_Value;

/**
 * 弹性域值集
 */
public interface IMDT_ValueSetDao {

	/**
	 * 获取弹性域值集
	 * @param valueSetId
	 * @return
	 */
	List<MDT_Value> getVelueSetRange(long valueSetId);
}
