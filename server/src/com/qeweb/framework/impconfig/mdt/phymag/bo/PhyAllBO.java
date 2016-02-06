package com.qeweb.framework.impconfig.mdt.phymag.bo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.DelRecordBO;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.mdt.phymag.sqlcreator.SQLCreator;
import com.qeweb.framework.impconfig.mdt.phymag.sqlcreator.mysql.MySqlCreator;

/**
 * 物理表管理BO
 */
public class PhyAllBO extends BusinessObject {

	private static final long serialVersionUID = -3748116678487858151L;
	
	private String sql;

	public PhyAllBO() {
		super();
		getBOP("sql").getStatus().setReadonly(true);
		
		OperateBOP saveBOP = new OperateBOP();
		saveBOP.setSaveMod("phyTphyColumnBOTable." + OperateBOP.SAVEMOD_MODIFY);
		addOperateBOP("save", saveBOP);
		
		OperateBOP viewAlertSql = new OperateBOP();
		viewAlertSql.setSaveMod("phyTphyColumnBOTable." + OperateBOP.SAVEMOD_MODIFY);
		addOperateBOP("viewAlterSql", viewAlertSql);
		
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	/**
	 * 新增操作，在数据库中创建表
	 * @param boList
	 * @throws BOException
	 */
	@SuppressWarnings("deprecation")
	public void insert(List<BusinessObject> boList) throws Exception {
		//表信息
		PhyTableBO phyTableBO = (PhyTableBO) boList.get(0);
		//列信息
		Set<PhyColumnBO> phyColumnSet = getPhyColumnSet(boList, phyTableBO);

		//向MDT相关表中插入物理表相关信息
		new PhyColumnBO().insert(phyTableBO, phyColumnSet);
		//在数据库中创建表
		SQLCreator sqlCreator = new MySqlCreator();
		getJDBCDao().executeSql(sqlCreator.createTable(phyTableBO, phyColumnSet));
	}
	
	/**
	 * 修改操作，修改数据库中的表
	 * @param boList
	 * @throws Exception 
	 */
	public void update(List<BusinessObject> boList) throws Exception {
		//表信息
		PhyTableBO phyTableBO = (PhyTableBO) boList.get(0);
		//列信息
		Set<PhyColumnBO> phyColumnSet = getPhyColumnSet(boList, phyTableBO);
		//动态删除的列信息
		List<Long> delColIds = getDelColIds(boList);
		
		new PhyColumnBO().update(phyTableBO, phyColumnSet, delColIds);
	}
	
	/**
	 * 查看建表SQL
	 * @param boList
	 * @return
	 * @throws Exception 
	 */
	public PhyAllBO viewCreateTableSql(List<BusinessObject> boList) throws Exception {
		//表信息
		PhyTableBO phyTableBO = (PhyTableBO) boList.get(0);
		//列信息
		Set<PhyColumnBO> phyColumnSet = getPhyColumnSet(boList, phyTableBO);
		
		if(phyTableBO.validate(phyTableBO) && new PhyColumnBO().validateAdd(phyColumnSet)) {
			SQLCreator sqlCreator = new MySqlCreator();
			setSql(sqlCreator.createTable(phyTableBO, phyColumnSet));
			BOHelper.initPreferencePage(this);
		}
		
		return this;
	}
	
	/**
	 * 查看改表SQL
	 * @param boList
	 * @return
	 * @throws Exception 
	 */
	public PhyAllBO viewAlterTableSql(List<BusinessObject> boList) throws Exception {
		//表信息
		PhyTableBO phyTableBO = (PhyTableBO) boList.get(0);
		//列信息
		Set<PhyColumnBO> phyColumnSet = getPhyColumnSet(boList, phyTableBO);
		//动态删除的列信息
		List<Long> delColIds = getDelColIds(boList);
		
		PhyColumnBO phyCol = new PhyColumnBO();
		if(phyTableBO.validate(phyTableBO) && phyCol.validateCommon(phyColumnSet)) {
			Set<PhyColumnBO> delCols = phyCol.getDelCols(phyTableBO, phyColumnSet, delColIds);
			Set<PhyColumnBO> modifyCols = phyCol.getModifyCols(phyTableBO, phyColumnSet);
			Set<PhyColumnBO> addCols = phyCol.getAddCols(phyTableBO, phyColumnSet);

			SQLCreator sqlCreator = new MySqlCreator();
			setSql(sqlCreator.alterTable(phyTableBO, delCols, modifyCols, addCols));
			BOHelper.initPreferencePage(this);
		}
		
		return this;
	}
	
	/**
	 * 获取动态删除的列信息id
	 * @param boList
	 * @return
	 */
	private List<Long> getDelColIds(List<BusinessObject> boList) {
		for(BusinessObject bo : boList) {
			if(bo instanceof DelRecordBO) {
				DelRecordBO delBO = (DelRecordBO) bo;
				if(delBO.getBindBO() instanceof PhyColumnBO)
					return delBO.getDelPks();
			}
		}
		
		return null;
	}
	
	/**
	 * 获取列信息(获取boList中的PhyColumnBO)
	 * @param boList
	 * @param phyTableBO
	 */
	private Set<PhyColumnBO> getPhyColumnSet(List<BusinessObject> boList, PhyTableBO phyTableBO) {
		Set<PhyColumnBO> phyColumnSet = new LinkedHashSet<PhyColumnBO>();
		for(int i = 0; i < boList.size(); i++) {
			if(boList.get(i) instanceof PhyColumnBO) {
				PhyColumnBO phyColBO = (PhyColumnBO)boList.get(i);
				String colName = StringUtils.removeAllSpace(phyColBO.getName());
				//忽略字段名称为空的信息
				if(StringUtils.isEmptyStr(colName))
					continue;
				//忽略平台固定字段
				if(StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_ID)
						|| StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_CREATEUSERID)
						|| StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_CREATETIME)
						|| StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_LASTMODIFYTIME)
						|| StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_LASTMODIFYUSERID)
						|| StringUtils.isEqualIgnoreCase(colName, IBaseDao.PHY_DELETEFLAG))
					continue;
				
				phyColBO.setPhyTable(phyTableBO);
				phyColumnSet.add(phyColBO);
			}
		}
		
		return phyColumnSet;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
