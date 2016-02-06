package com.qeweb.sysmanage.purview.middletable;

import com.qeweb.framework.base.middletable.MiddleTable;
import com.qeweb.framework.base.middletable.MiddleTableEntity;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;

public class Role_Module_MiddleTable extends MiddleTable {
	private static final long serialVersionUID = -1418999746155989441L;

	public Role_Module_MiddleTable(String tableName) {
		super(tableName);
	}

	/**
	 * 删除旧记录<br>
	 * 根据主表ID和副表ID
	 * @param oldMajorTable
	 */
	public void delete(MiddleTableEntity oldMajorTable, MiddleTableEntity viceTable) {
		//删除旧记录
		if(StringUtils.isEmpty(oldMajorTable.getIds())
				|| ContainerUtil.isNull(oldMajorTable.getIdList())) 
			return;
		
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(this.getTableName())
			.append(" WHERE ")
			.append(oldMajorTable.getFieldName())
			.append(" = ? AND ")
			.append(viceTable.getFieldName())
			.append(" = ?");
		
		Object[] param = new Object[2];
		param[0] = oldMajorTable.getBo().getId();
		
		for(String id : oldMajorTable.getIdList()){
			param[1] = id;
			getDao().delete(sql.toString(), param);
		}
	}
	
	@Override
	public void insert(MiddleTableEntity majorTable, MiddleTableEntity viceTable)
			throws Exception {
		
		if(StringUtils.isEmpty(viceTable.getIds())
				|| ContainerUtil.isNull(viceTable.getIdList())) 
			return;
		
		for (String viceTableId : viceTable.getIdList()) {
			insertRecord(majorTable, viceTable, viceTableId);
		}
		
	}
}
