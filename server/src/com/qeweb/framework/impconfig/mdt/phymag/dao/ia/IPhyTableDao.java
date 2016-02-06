package com.qeweb.framework.impconfig.mdt.phymag.dao.ia;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyTableBO;

/**
 * 物理表相关dao接口
 */
public interface IPhyTableDao extends IBaseDao {

	/**
	 * 获取物理表中的所有数据
	 * @return
	 */
	List<PhyTableBO> findAll();
	
	/**
	 * 根据ID查询未删除的PhyTableBO
	 * @param id
	 * @return
	 */
	PhyTableBO findById(long id);
	
	/**
	 * 根据tableName和tableAlias查询未删除的PhyTableBO
	 * @param tableName
	 * @param tableAlias
	 * @return
	 */
	PhyTableBO findUndelByName(String tableName, String tableAlias);
	
	/**
	 * 删除phyTableList
	 */
	void delete(List<BusinessComponent> bcList);
}
