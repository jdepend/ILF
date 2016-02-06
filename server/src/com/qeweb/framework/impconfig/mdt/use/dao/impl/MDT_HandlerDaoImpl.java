package com.qeweb.framework.impconfig.mdt.use.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.impconfig.mdt.use.bean.MDTColumn;
import com.qeweb.framework.impconfig.mdt.use.bean.MDTVertical;
import com.qeweb.framework.impconfig.mdt.use.dao.ia.IMDT_HandlerDao;

public class MDT_HandlerDaoImpl implements IMDT_HandlerDao {
	
	/**
	 * 解析弹性域的固定列
	 * @param tableName
	 * @return
	 */
	@Override
	final public List<MDTColumn> interpretFlexFixed(String tableName) {
		String sql = 
			"SELECT * from qeweb_mdt_table_fields mdt_fields, qeweb_mdt_tables mdt_table " +
			"where mdt_fields.DELETE_FLAG = " + IBaseDao.UNDELETE_SIGNE + " and mdt_table.DELETE_FLAG = " + IBaseDao.UNDELETE_SIGNE + 
			" and mdt_table.TABLE_NAME = '" + tableName + "'" +
			" and mdt_fields.TABLE_ID = mdt_table.id";
		
		return exeInterpretSql(sql);
	}
	
	
	/**
	 * 解析弹性域的分类列
	 * @param calssifyValue 分类列值
	 * @param tableName		拥有弹性域的表名
	 * @return
	 */
	@Override
	final public List<MDTColumn> interpretFlexClassify(String calssifyValue, String tableName) {
		String sql = 
			"select * from qeweb_mdt_flex_segment mdt_segment where mdt_segment.DELETE_FLAG = 0 " 
			+ "and mdt_segment.FLEX_CLASSIFY_COOD_ID in ("
				+ "select id from qeweb_mdt_flex_classify_code mdt_classify_code where mdt_classify_code.delete_flag = " + IBaseDao.UNDELETE_SIGNE
					//分类列值
					+ " and CLASSIFY_VALUE = '" + calssifyValue + "'"
					+ " and mdt_classify_code.FLEX_CLASSIFY_ID in ( "
					+ "select id from qeweb_mdt_flex_classify mdt_calsify where mdt_calsify.DELETE_FLAG = " + IBaseDao.UNDELETE_SIGNE + " and mdt_calsify.table_id  in "
						+ "(select id from qeweb_mdt_tables mdt_table " 	
							+ "where mdt_table.delete_flag = " + IBaseDao.UNDELETE_SIGNE
							+ " and mdt_table.table_name = '" + tableName + "'" 
						+ ")"
					+ ")"
			+ ")";
		
		return exeInterpretSql(sql);
	}
	
	@SuppressWarnings("unchecked")
	private List<MDTColumn> exeInterpretSql(String sql) {
		RowMapper mapper = new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	MDTColumn result = new MDTColumn();
            	result.setName(rs.getString("name"));
            	result.setAlias(AppLocalization.getLocalization(rs.getString("alias")));
            	return result;
            }
		};
		
		return BaseDaoInfo.getJDBCDao().findBySql(sql, mapper);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDTVertical> getVerticalExt(long mdtFieldId) {
		DetachedCriteria dc = DetachedCriteria.forClass(MDTVertical.class);
		dc.add(Restrictions.eq("mdtFieldId", mdtFieldId));
		
		return BaseDaoInfo.getDao().findByCriteria(dc);
	}
}
