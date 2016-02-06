package com.qeweb.framework.base.middletable;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 中间表
 */
public class MiddleTable implements Serializable {

	private static final long serialVersionUID = 748973753911961514L;
	private long createUserId;								//创建人
	private Timestamp createTime;							//创建时间
	private long lastModifyUserId; 							//最后修改人
	private Timestamp lastModifyTime; 						//最后修改时间
	private String tableName;								//操作表的名称
	
	//数据库字段
	final static public String FIELD_CREATE_TIME = "CREATE_TIME"; 					//创建时间
	final static public String FIELD_CREATE_USER_ID = "CREATE_USER_ID"; 			//创建人
	final static public String FIELD_LAST_MODIFY_USER_ID = "LAST_MODIFY_USER_ID"; 	//最后修改人
	final static public String FIELD_LAST_MODIFY_TIME = "LAST_MODIFY_TIME"; 		//最后修改时间
	
	public MiddleTable(){}
	
	public MiddleTable(String tableName){
		this.tableName = tableName;
	}

	/**
	 * 持久化操作-插入
	 * @param majorTable		主表实体
	 * @param viceTable			副表实体
	 * @throws Exception
	 */
	public void insert(MiddleTableEntity majorTable, MiddleTableEntity viceTable) throws Exception {
		delete(majorTable);
		if(StringUtils.isNotEmpty(viceTable.getIds())) {
			for (String viceTableId : viceTable.getIds()) {
				insertRecord(majorTable, viceTable, viceTableId);
			}
		}
		else if(ContainerUtil.isNotNull(viceTable.getIdList())) {
			for (String viceTableId : viceTable.getIdList()) {
				insertRecord(majorTable, viceTable, viceTableId);
			}
		}
	}

	
	/**
	 * 持久化操作-修改
	 * @param majorTable		主表
	 * @param viceTable			副表
	 * @throws Exception
	 */
	public void update(MiddleTableEntity majorTable, MiddleTableEntity viceTable) throws Exception {
		insert(majorTable, viceTable);
	}

	/**
	 * 删除旧记录, 用于update和insert, 在增/改之前使用
	 * @param oldMajorTable
	 */
	public void delete(MiddleTableEntity oldMajorTable) {
		//删除旧记录
		String sql = "DELETE FROM " + this.getTableName();
		sql += " WHERE " + oldMajorTable.getFieldName() + " = ?";
		
		Object[] param = new Object[1];
		param[0] = oldMajorTable.getBo().getId();
		getDao().delete(sql, param);
	}
	
	/**
	 * 用户删除中间表的数据, 可直接用于删除
	 * @param oldMajorTable
	 */
	public void deleteAll(MiddleTableEntity oldMajorTable) {
		String[] ids = oldMajorTable.getIds();
		if(StringUtils.isEmpty(ids))
			return;
		
		//删除旧记录
		String sql = "DELETE FROM " + this.getTableName();
		sql += " WHERE " + oldMajorTable.getFieldName() + " = ?";
		for(int i = 0; i < ids.length; i++) {
			getDao().delete(sql, new String[] {ids[i]});
		}
	}
	
	/**
	 * 根据主表ID获取所有副表ID
	 * @param majorTable	主表
	 * @param viceTable		副表
	 * @param majorId		主表ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findViceIds(MiddleTableEntity majorTable, MiddleTableEntity viceTable, long majorId) {
		String sql = "SELECT " + viceTable.getFieldName() + " FROM " + this.getTableName();
		sql += " WHERE " + majorTable.getFieldName() + " = " + majorId;
		
		final String fileName = viceTable.getFieldName();
		RowMapper mapper = new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getString(fileName);
            }
		};
		
		return getJDBCDao().findBySql(sql, mapper);
	}
	
	/**
	 * 写入一条记录
	 * @param majorTable
	 * @param viceTable
	 * @param viceTableId
	 */
	protected void insertRecord(MiddleTableEntity majorTable,
			MiddleTableEntity viceTable, String viceTableId) {
		Map<String, Object> publicParam = getPublicParameter(IBaseDao.UNDELETE_SIGNE);
		// 拼装sql
		String sqlPrefix = "insert into " + this.tableName + "(";// sql前段
		String sqlSuffix = "values(";// sql后段

		Object[] param = new Object[publicParam.size() + 2];
		int i = 0;
		Iterator<String> keys = publicParam.keySet().iterator();
		// 循环拼装sql，公共参数
		while (keys.hasNext()) {
			String fieldName = keys.next();// 字段名
			sqlPrefix += fieldName + ",";
			sqlSuffix += "?,";
			param[i] = publicParam.get(fieldName);// 字段对应的值
			i++;
		}
		sqlPrefix += majorTable.getFieldName() + ",";
		sqlPrefix += viceTable.getFieldName() + ")";
		sqlSuffix += "?,?)";
		sqlPrefix += sqlSuffix;// 最终sql
		param[i++] = majorTable.getBo().getId();// 主表的ID
		param[i] = viceTableId;// 副表的ID
		getDao().save(sqlPrefix, param);
	}
	
	/**
	 * 获取中间表公共字段和对应值。
	 * @param isLastSign	是否最新标记
	 * @param deleteFlag	删除标记
	 * @return
	 */
	protected Map<String, Object> getPublicParameter(int deleteFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(UserContext.getUserBO() != null) {
			map.put(FIELD_CREATE_USER_ID, UserContext.getUserId());
			map.put(FIELD_LAST_MODIFY_USER_ID, UserContext.getUserId());
		}
		else {
			map.put(FIELD_CREATE_USER_ID, 0);
			map.put(FIELD_LAST_MODIFY_USER_ID, 0);
		}
		map.put(FIELD_CREATE_TIME, DateUtils.getCurrentTimestamp());
		map.put(FIELD_LAST_MODIFY_TIME, DateUtils.getCurrentTimestamp());
		return map;
	}
	
	public IBaseDao getDao() {
		return BaseDaoInfo.getDao();
	}
	
	public IBaseDao getJDBCDao() {
		return BaseDaoInfo.getJDBCDao();
	}
	
	public long getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
