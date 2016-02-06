package com.qeweb.framework.impconfig.ddt.use;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.impconfig.ddt.use.dao.ia.IDDTSchemaDao;
import com.qeweb.framework.impconfig.ddt.use.dao.impl.DDTSchemaDaoImpl;

/**
 * DDT方案处理者
 */
public class DDTSchemaHandler {

	final private static IDDTSchemaDao DDTSchemaDao = new DDTSchemaDaoImpl();
	
	
	//默认DDT方案中的所有组件
	private static List<DDTSchema> ddtDefSchemaList = null;

	/**
	 * 获取当前登录用户对应的DDTSchemaList, 如果没有为当前登录用户配置单独的schema, 则返回默认的schema
	 * @param ddtAppConfig
	 * @return
	 */
	final public static List<DDTSchema> getAppropriateSchema(DDTAppConfig ddtAppConfig) {
		DDTAppConfig result = DDTSchemaDao.getDDTAppConfig(ddtAppConfig);
		
		if(result != null && StringUtils.isNotEmpty(result.getSchemaCode()))
			return DDTSchemaDao.getDDTSchemaList(result.getSchemaCode());
		else
			return getDefDDTSchema();
	}
	
	/**
	 * 根据页面URL和粗粒度组件ID 获取DDT方案中的细粒度组件
	 * @param sourcePage
	 * @param containerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final static public List<DDTSchema> getDDTFC(String sourcePage, String containerId) {
		List<DDTSchema> ddtList = (List<DDTSchema>)MsgService.getMsg(Constant.DDT_CONTAINER);
		if(ContainerUtil.isNull(ddtList))
			return null;
		
		List<DDTSchema> result = new LinkedList<DDTSchema>();
		for(DDTSchema ddtFC : ddtList) {
			if(StringUtils.isEqualStr(ddtFC.getSourcePage(), sourcePage)
				&& StringUtils.isEqualStr(ddtFC.getContainerId(), containerId))
				result.add(ddtFC);
		}
			
		return result;
	}
	
	/**
	 * 获取默认DDT方案中的所有组件. 默认方案即getDefSchemaCode()对应的方案.
	 * @return DDTSchemaList
	 */
	static private List<DDTSchema> getDefDDTSchema() {
		if(ddtDefSchemaList == null || AppConfig.isDebug())
			ddtDefSchemaList = DDTSchemaDao.getDDTSchemaList(getDefSchemaCode());
		 
		return ddtDefSchemaList;
	}
	
	/**
	 * 获取默认schema方案编码, 默认编码通过application.properties中的schemaCode指定, 如果没有没有指定, 则默认编码为"def"
	 * @return schemaCode
	 */
	static private String getDefSchemaCode() {
		String defSchemaCode = "def";
		String schemaCode = AppConfig.getPropValue(ConstantAppProp.SCHEMA_CODE);
		return StringUtils.isEmpty(schemaCode) ? defSchemaCode : schemaCode;
	}
}
