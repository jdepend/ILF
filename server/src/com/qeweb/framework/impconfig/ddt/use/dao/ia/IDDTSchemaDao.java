package com.qeweb.framework.impconfig.ddt.use.dao.ia;

import java.util.List;

import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;

/**
 *  DDT方案 dao
 */
public interface IDDTSchemaDao {

	/**
	 * 根据ddtAppConfig获取DDTAppConfig
	 * @param ddtAppConfig
	 * @return
	 */
	DDTAppConfig getDDTAppConfig(DDTAppConfig ddtAppConfig);
		
	/**
	 * 查询DDT方案中的所有组件
	 * @param schemaCode 方案编码
	 * @return
	 */
	List<DDTSchema> getDDTSchemaList(String schemaCode);
}
