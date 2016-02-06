package com.qeweb.demo.mobile.dao.ia;

import com.qeweb.demo.mobile.bo.DemoMobileBaseBO;
import com.qeweb.framework.base.ia.IBaseDao;

/**
 * 移动巡检dao
 */
public interface IDemoMobileBaseDao extends IBaseDao {

	/**
	 * 根据门店编码，巡检员ID查询暂存的巡检信息
	 * @param shopId
	 * @param vistorId
	 * @param clasz
	 * @return
	 */
	DemoMobileBaseBO findSaveInfo(long shopId, long vistorId, Class<? extends DemoMobileBaseBO> clasz) throws Exception;

	/**
	 * 
	 * @param comparateId
	 * @param calsz
	 * @return
	 */
	DemoMobileBaseBO find(String comparateId, Class<? extends DemoMobileBaseBO> calsz) throws Exception;
}
