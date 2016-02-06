package com.qeweb.framework.impconfig.mdt.phymag.sqlcreator.mysql;

import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyColumnBO;
import com.qeweb.framework.impconfig.mdt.phymag.bo.PhyTableBO;
import com.qeweb.framework.impconfig.mdt.phymag.bop.PhyColDataTypeBOP;
import com.qeweb.framework.impconfig.mdt.phymag.bop.PhyColIsNotNullBOP;
import com.qeweb.framework.impconfig.mdt.phymag.sqlcreator.SQLCreator;

/**
 * Sql生成器的mySql实现
 */
public class MySqlCreator implements SQLCreator {

	/** mySql建表语句格式：
	 CREATE TABLE QEWEB_MDT_PHY_TABLE
	(
	   ID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	   NAME VARCHAR(30) NOT NULL COMMENT '表名',
	   ALIAS VARCHAR(30) COMMENT '别名',
	   REMARK VARCHAR(128) COMMENT '备注',
	   -- 4个平台字段
	   DELETE_FLAG INT(1) DEFAULT 0,
	   CREATE_USER_ID INT(11),
	   CREATE_TIME DATETIME,
	   LAST_MODIFY_USER_ID INT(11),
	   LAST_MODIFY_TIME DATETIME)
	);
	ALTER TABLE QEWEB_MDT_PHY_TABLE COMMENT '物理表信息';
	*/
	@Override
	public String createTable(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColBOSet) {
		StringBuilder sbr = new StringBuilder();
		String tableName = StringUtils.toUpperCase(phyTableBO.getName());
		sbr.append("CREATE TABLE ").append(tableName).append("(");
		//添加ID字段
		sbr.append(IBaseDao.PHY_ID).append(" INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,");
		
		for(PhyColumnBO colBO : phyColBOSet) {
			sbr.append(getColumnDef(colBO)).append(",");
		}
		
		//添加4个平台字段
		sbr.append(IBaseDao.PHY_DELETEFLAG).append(" INT(1) DEFAULT ").append(IBaseDao.UNDELETE_SIGNE).append(",");
		sbr.append(IBaseDao.PHY_CREATEUSERID).append(" INT(11),");
		sbr.append(IBaseDao.PHY_CREATETIME).append(" DATETIME,");
		sbr.append(IBaseDao.PHY_LASTMODIFYUSERID).append(" INT(11),");
		sbr.append(IBaseDao.PHY_LASTMODIFYTIME).append("LAST_MODIFY_TIME DATETIME)");
		//添加注释
		sbr.append(" COMMENT '").append(phyTableBO.getRemark()).append("';");
		
		return sbr.toString();
	}
	
	/**
	 * 获取数据库字段的数据类型
	 * @param dataType
	 * @return
	 */
	private String getSqlDataType(String dataType) {
		if(PhyColDataTypeBOP.isInt(dataType))
			return "INT";
		else if(PhyColDataTypeBOP.isString(dataType))
			return "VARCHAR";
		else if(PhyColDataTypeBOP.isDate(dataType))
			return "DATETIME";
		else 
			return "";
	}

	@Override
	public String alterTable(PhyTableBO phyTableBO, Set<PhyColumnBO> delCols,
			Set<PhyColumnBO> modifyCols, Set<PhyColumnBO> addCols) {
		String alterTableSql = getAlterTableSql(phyTableBO);
		String delColSql = getDelColumnSql(phyTableBO, delCols); 
		String addColSql = getAddColumnSql(phyTableBO, addCols);
		String modifyColSql = getAlterColumnSql(phyTableBO, modifyCols);
		
		return alterTableSql + delColSql + addColSql + modifyColSql;
	}

	/**
	 * 修改表信息SQL
	 * <li> 1.修改了表名: ALTER TABLE OLD_NAME RENAME TO NEW_NAME COMMENT 'COMMENT';
	 * <li> 2.仅修改了注释: ALTER TABLE TABLE COMMENT 'NEW COMMENT';
	 * @param phyTableBO 表基本信息
	 * @return
	 */
	private String getAlterTableSql(PhyTableBO phyTableBO) {
		String sql = "";
		PhyTableBO oldTable = phyTableBO.getRecord(phyTableBO.getId());
		//未改变表名
		if(StringUtils.isEqualIgnoreCase(phyTableBO.getName(), oldTable.getName())) {
			//改变了注释内容
			if(!StringUtils.isEqual(phyTableBO.getRemark(), oldTable.getRemark()))
				sql = "ALTER TABLE " + oldTable.getName() + " COMMENT " + phyTableBO.getRemark();
		}
		else {
			sql = "ALTER TABLE " + oldTable.getName() + " RENAME TO " + StringUtils.toUpperCase(phyTableBO.getName());
			//改变了注释内容
			if(!StringUtils.isEqual(phyTableBO.getRemark(), oldTable.getRemark()))
				sql += " COMMENT " + phyTableBO.getRemark();
		}
		
		return sql;
	}
	
	/**
	 *  删除字段SQL
	 * <li> ALTER TABLE T2 DROP COLUMN C;
	 * @param phyTableBO 表基本信息
	 * @param delCols 待删除的字段
	 * @return
	 */
	private String getDelColumnSql(PhyTableBO phyTableBO, Set<PhyColumnBO> delCols) {
		if(ContainerUtil.isNull(delCols))
			return "";
		
		StringBuilder sql = new StringBuilder("");
		for(PhyColumnBO column : delCols) {
			sql.append("ALTER TABLE ").append(phyTableBO.getName()).append(" DROP CLOUMN ").append(column.getName()).append(";");
		}
		
		return sql.toString();
	}
	
	/**
	 * 新增字段SQL
	 * <li>ALTER TABLE INFOS ADD EX INT NOT NULL DEFAULT '0';
	 * <li>ALTER TABLE INFOS ADD EX2 VARCHAR(20) NOT NULL DEFAULT 'VVV';
	 * @param phyTableBO 表基本信息
	 * @param addCols    待新增的字段
	 * @return
	 */
	private String getAddColumnSql(PhyTableBO phyTableBO, Set<PhyColumnBO> addCols) {
		if(ContainerUtil.isNull(addCols))
			return "";
		
		StringBuilder sql = new StringBuilder("");
		for(PhyColumnBO column : addCols) {
			sql.append("ALTER TABLE ").append(phyTableBO.getName()).append(" ADD ").append(getColumnDef(column)).append(";");
		}
		
		return sql.toString();
	}

	/**
	 * 修改字段SQL. 
	 * 如:ALTER TABLE TABLENAME CHANGE DEPNO DEPNO INT(5) NOT NULL;
	 * @return
	 */
	private String getAlterColumnSql(PhyTableBO phyTableBO, Set<PhyColumnBO> modifyCols) {
		if(ContainerUtil.isNull(modifyCols))
			return "";
		
		StringBuilder sql = new StringBuilder("");
		for(PhyColumnBO column : modifyCols) {
			sql.append("ALTER TABLE ").append(phyTableBO.getName()).append(" CHANGE ").append(getColumnDef(column)).append(";");
		}
		
		return sql.toString();
	}
	
	/**
	 * 根据colBO获取字段定义sql. 如: NAME VARCHAR(30) NOT NULL COMMENT '表名'
	 * @param colBO
	 * @return
	 */
	private String getColumnDef(PhyColumnBO colBO) {
		StringBuilder sql = new StringBuilder("");
		String dataType = getSqlDataType(colBO.getDataType());
		if (StringUtils.isEqual("DATETIME", dataType)) {
			sql.append(colBO.getName()).append(" DATETIME");
			if (PhyColIsNotNullBOP.isNotNull(colBO.getIsNotNull()))
				sql.append(" NOT NULL");
			sql.append(" COMMENT '").append(colBO.getRemark()).append("'");
		} 
		else if (StringUtils.isNotEmpty(dataType)) {
			int maxLength = 11;
			if (colBO.getMaxLength() != null)
				maxLength = colBO.getMaxLength();
			sql.append(colBO.getName()).append(" ").append(dataType).append("(").append(maxLength).append(")");
			if (PhyColIsNotNullBOP.isNotNull(colBO.getIsNotNull()))
				sql.append(" NOT NULL");
			if (StringUtils.isNotEmpty(colBO.getDefValue()))
				sql.append(" DEFAULT '").append(colBO.getDefValue()).append("'");

			sql.append(" COMMENT '").append(colBO.getRemark()).append("'");
		}
		
		return sql.toString();
	}
}
