/**
 * 
 */
package com.qeweb.framework.impconfig.ddt.config.bo;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.frameworkbop.EmptyBopDec;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;

/**
 * DDT应用配置
 */
public class DdtAppConfigBO extends BusinessObject {

	private static final long serialVersionUID = -3466882493385680767L;
	private String schemaCode; 	// 方案编码
	private String appName; 	// 应用名(应用名属于全局配置, 不能在页面配置)
	private String userCode;	// 用户编码
	private String roleName;	// 角色名称
	private String attr1;
	private String attr2;
	private String attr3;

	public DdtAppConfigBO() {
		super();
		setAppName(AppConfig.getDDTAppName());
		getBOP("appName").getStatus().setReadonly(true);
		getBOP("appName").setValue(AppConfig.getDDTAppName());
		addBOP("schemaCode", new NotEmptyBopDec(new BOProperty(), 1, 50));
		addBOP("userCode", new EmptyBopDec(new BOProperty(), 50));
		addBOP("roleName", new EmptyBopDec(new BOProperty(), 50));
		addBOP("attr1", new EmptyBopDec(new BOProperty(), 50));
		addBOP("attr2", new EmptyBopDec(new BOProperty(), 50));
		addBOP("attr3", new EmptyBopDec(new BOProperty(), 50));
		addOperateBOP("goback", new NOSubmitBOP());
	}

	public void delete() {
		BOHelper.setBOPublicFields_delete(this);
		getDao().update(this);		
	}

	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new HashMap<String, String>();
		orderMap.put("schemaCode", IBaseDao.ORDER_BY_DESC);
		
		return orderMap;
	}

	public String getSchemaCode() {
		return schemaCode;
	}

	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
}
