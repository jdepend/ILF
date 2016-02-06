package com.qeweb.framework.impconfig.ddt.use.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTAppConfig;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.impconfig.ddt.use.dao.ia.IDDTSchemaDao;

/**
 * DDT方案 dao impl
 */
public class DDTSchemaDaoImpl implements IDDTSchemaDao {

	@SuppressWarnings("unchecked")
	@Override
	final public DDTAppConfig getDDTAppConfig(DDTAppConfig ddtAppConfig) {
		DetachedCriteria dc = DetachedCriteria.forClass(DDTAppConfig.class);
		
		if(StringUtils.isNotEmpty(ddtAppConfig.getAppName())) 
			dc.add(Restrictions.eq("appName", ddtAppConfig.getAppName()));
		if(StringUtils.isNotEmpty(ddtAppConfig.getUserCode()))
			dc.add(Restrictions.eq("userCode", ddtAppConfig.getUserCode()));
		if(StringUtils.isNotEmpty(ddtAppConfig.getRoleName())) 
			dc.add(Restrictions.eq("roleName", ddtAppConfig.getRoleName()));
		if(StringUtils.isNotEmpty(ddtAppConfig.getAttr_1()))
			dc.add(Restrictions.eq("attr_1", ddtAppConfig.getAttr_1()));
		if(StringUtils.isNotEmpty(ddtAppConfig.getAttr_2()))
			dc.add(Restrictions.eq("attr_2", ddtAppConfig.getAttr_2()));
		if(StringUtils.isNotEmpty(ddtAppConfig.getAttr_3()))
			dc.add(Restrictions.eq("attr_3", ddtAppConfig.getAttr_3()));
		
		List<DDTAppConfig> result = (List<DDTAppConfig>)BaseDaoInfo.getDao().findByCriteria(dc);
		if(ContainerUtil.isNull(result))
			return null;
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	final public List<DDTSchema> getDDTSchemaList(String schemaCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(DDTSchema.class);
		dc.add(Restrictions.eq("schemaCode", schemaCode));
		
		return BaseDaoInfo.getDao().findByCriteria(dc);
	}
}
