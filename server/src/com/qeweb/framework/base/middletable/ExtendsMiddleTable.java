package com.qeweb.framework.base.middletable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 中间表，可追朔历史
 */
public class ExtendsMiddleTable extends MiddleTable {

	private static final long serialVersionUID = 6772456028218449893L;
	private long groupId = 0L;
	private long groupOldId = IBaseDao.OLD_NO;				//默认为0
	private int deleteFlag = IBaseDao.UNDELETE_SIGNE;		//删除标识，默认不删除,供hbm使用
	
	//数据库字段
	final static public String FIELD_GROUP_ID = "GROUP_ID"; 				
	final static public String FIELD_GROUP_OLD_ID = "GROUP_OLD_ID"; 		
	final static public String FIELD_DELETE_FLAG = "DELETE_FLAG"; 			//删除标识
	
	public ExtendsMiddleTable(){
		
	}
	
	public ExtendsMiddleTable(String tableName){
		setTableName(tableName);
	}

	/**
	 * 持久化操作-插入
	 * @param majorTable		主表实体
	 * @param viceTable			副表实体
	 * @throws BOException
	 */
	public void insert(MiddleTableEntity majorTable, MiddleTableEntity viceTable) throws Exception {	
		if(viceTable.getIds().length > 0) {
			if(groupId == 0L) 
				groupId = getMaxGroupId(getTableName()) + 1;
			for(String viceTableId : viceTable.getIds()){
				Map<String, Object> publicParam = getPublicParameter(groupId);
				//拼装sql
				String sqlPrefix = "insert into " + this.getTableName() + "(";//sql前段
				String sqlSuffix = "values(";//sql后段
				
				Object[] param = new Object[publicParam.size() + 2];
				int i = 0;
				Iterator<String> keys = publicParam.keySet().iterator();
				//循环拼装sql，公共参数
				while (keys.hasNext()) {
					String fieldName = keys.next();//字段名
					sqlPrefix += fieldName + ",";
					sqlSuffix += "?,";
					param[i] = publicParam.get(fieldName);//字段对应的值
					i++;
				}
				sqlPrefix += majorTable.getFieldName() + ",";
				sqlPrefix += viceTable.getFieldName() + ")";
				sqlSuffix += "?,?)";
				sqlPrefix += sqlSuffix;//最终sql
				param[i++] = majorTable.getBo().getId();//主表的ID
				param[i] = viceTableId;//副表的ID
				getDao().save(sqlPrefix, param);
			}
		}
	}
	
	/**
	 * 持久化操作-删除
	 * @param fieldName		中间表字段名
	 * @param boList		bo列表
	 * @throws Exception
	 */
	public void delete(String fieldName, List<BusinessComponent> bcList) throws Exception {
		if(ContainerUtil.isNull(bcList))
			return;
		Object[] param = new Object[3];
		if(UserContext.getUserBO() != null) 
			param[0] = UserContext.getUserId();
		for(BusinessComponent bc : bcList) {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ").append(getTableName());
			sql.append(" SET ").append(FIELD_DELETE_FLAG).append(" = ").append(IBaseDao.DELETE_SIGNE).append(",");
			sql.append(FIELD_LAST_MODIFY_USER_ID).append(" = ?,");
			sql.append(FIELD_LAST_MODIFY_TIME).append(" = ? ");
			sql.append("WHERE ").append(fieldName).append(" = ? ");
			sql.append("AND ").append(FIELD_GROUP_OLD_ID).append(" = ").append(IBaseDao.OLD_NO);
			param[1] = DateUtils.getCurrentTimestamp();
			param[2] = bc.getId();
			getDao().update(sql.toString(), param);
		}
	}
	
	/**
	 * 持久化操作-插入
	 * @param oldMajorTable		旧主表
	 * @param newMajorTable		新主表
	 * @param viceTable			副表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void update(MiddleTableEntity oldMajorTable, MiddleTableEntity newMajorTable, MiddleTableEntity viceTable) throws Exception {
		StringBuilder select = new StringBuilder();
		select.append("SELECT ");
		select.append(oldMajorTable.getFieldName()).append(",");
		select.append(viceTable.getFieldName()).append(",");
		select.append(FIELD_GROUP_ID);
		select.append(" FROM ").append(getTableName()).append(" ");
		select.append("WHERE ").append(oldMajorTable.getFieldName()).append(" = ").append(oldMajorTable.getBo().getId());
		select.append(" AND ").append(FIELD_GROUP_OLD_ID).append(" = ").append(IBaseDao.OLD_NO);
		
		List<Object[]> list = getDao().createQuery(select.toString());
		long oldGroupId = 0L;
		if(ContainerUtil.isNotNull(list))
			oldGroupId = Long.valueOf(list.get(0)[2].toString());
		
		//备份旧记录
		String viceTableIds = "";
		for (int i = 0; i < list.size(); i++) {
			viceTableIds += list.get(i)[1] + ",";
		}
		viceTableIds = StringUtils.removeEnd(viceTableIds);
		if(StringUtils.isNotEmpty(viceTableIds)) {
			MiddleTableEntity oldViceTable = new MiddleTableEntity(viceTable.getFieldName(), viceTableIds.split(","));
			setGroupOldId(oldGroupId);
			insert(oldMajorTable, oldViceTable);
		}
		
		//删除旧记录，即更新删除标记为1
		StringBuilder delete = new StringBuilder();
		delete.append("DELETE FROM  ").append(getTableName());
		delete.append(" WHERE ").append(oldMajorTable.getFieldName()).append(" = ").append(oldMajorTable.getBo().getId());
		delete.append(" AND ").append(FIELD_GROUP_OLD_ID).append(" = ").append(IBaseDao.OLD_NO);
		getDao().delete(delete.toString(), null);
		
		//插入新记录
		setGroupId(oldGroupId);
		setGroupOldId(0);
		insert(newMajorTable, viceTable);
	}
	
	public Integer getMaxGroupId(String tableName){
		return (Integer) getDao().createQueryUniqueResult("SELECT MAX("+ FIELD_GROUP_ID +") FROM " + tableName);
	}
	
	public long getGroupOldId(String tableName, MiddleTableEntity entity){
		Long oldId = 0L;
		if(entity != null){
			String id = (String) getDao().createQueryUniqueResult("SELECT MAX("+ FIELD_GROUP_ID +") FROM " + tableName 
					+ " WHERE " + entity.getFieldName() + "=" + entity.getBo().getId());
			oldId = id != null ? Long.valueOf(id) : oldId;
		}
		return oldId; 
	}
	
	/**
	 * 获取中间表公共字段和对应值。
	 * @param isLastSign	是否最新标记
	 * @param deleteFlag	删除标记
	 * @return
	 */
	public Map<String, Object> getPublicParameter(long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FIELD_GROUP_ID, groupId);
		map.put(FIELD_GROUP_OLD_ID, getGroupOldId());
		map.put(FIELD_DELETE_FLAG, IBaseDao.UNDELETE_SIGNE);
		if(UserContext.getUserBO() != null) {
			map.put(FIELD_CREATE_USER_ID, UserContext.getUserId());
			map.put(FIELD_LAST_MODIFY_USER_ID, UserContext.getUserId());
		}
		map.put(FIELD_CREATE_TIME, DateUtils.getCurrentTimestamp());
		map.put(FIELD_LAST_MODIFY_TIME, DateUtils.getCurrentTimestamp());
		return map;
	}
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getGroupOldId() {
		return groupOldId;
	}

	public void setGroupOldId(long groupOldId) {
		this.groupOldId = groupOldId;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
