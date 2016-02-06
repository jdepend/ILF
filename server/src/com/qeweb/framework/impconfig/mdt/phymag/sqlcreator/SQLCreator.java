package com.qeweb.framework.impconfig.mdt.phymag.sqlcreator;

import java.util.Set;

import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyColumnBO;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyTableBO;

/**
 * Sql生成器, 负责根据物理表修改相关信息(PhyTableBO,PhyColumnBO)生成sql
 */
public interface SQLCreator {

	/**
	 * 生成建表语句
	 * @param phyTableBO
	 * @param phyColBOSet
	 * @return 建表语句
	 */
	String createTable(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColBOSet);
	
	/**
	 * 生成修改表语句
	 * @param phyTableBO	
	 * @param delCols		待删除的字段信息
	 * @param modifyCols	待修改的字段信息
	 * @param addCols		待添加的字段信息
	 * @return 修改表语句
	 */
	String alterTable(PhyTableBO phyTableBO, Set<PhyColumnBO> delCols, 
			Set<PhyColumnBO> modifyCols, Set<PhyColumnBO> addCols);
}
