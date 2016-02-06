package com.qeweb.framework.base.impl;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.common.SpringConstant;

/**
 * 从spring配置 中获取默认 dao
 */
public class BaseDaoInfo {

	final public static IBaseDao getDao(){
		return (BaseDaoHibImpl)SpringConstant.getCTX().getBean("BaseDao");
	}
	
	final public static IBaseDao getJDBCDao(){
		return (BaseDaoJDBCImpl)SpringConstant.getCTX().getBean("JDBCDao");
	}
	
	final public static IXmlDao getXmlDao() {
		return (XmlDaoImpl)SpringConstant.getCTX().getBean("XmlDao");
	}
}
