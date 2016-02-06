package com.qeweb.framework.impconfig.mdt.phymag.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyColumnBO;

/**
 * 物理表字段相关dao接口
 */
public interface IPhyColumnDao extends IBaseDao {

	/**
	 * 根据物理表的name，查找物理表的字段
	 * @return
	 */
	public List<PhyColumnBO> getPhyColumnsByPhyTableName(String tableName);
}
