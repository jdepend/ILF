package com.qeweb.framework.impconfig.mdt.use.dao.ia;

import java.util.List;

import com.qeweb.framework.impconfig.mdt.use.bean.MDTColumn;
import com.qeweb.framework.impconfig.mdt.use.bean.MDTVertical;

/**
 * MDT处理dao
 */
public interface IMDT_HandlerDao {
	
	/**
	 * 解析弹性域的固定列
	 * @param tableName
	 * @return
	 */
	List<MDTColumn> interpretFlexFixed(String tableName);
	
	
	/**
	 * 解析弹性域的分类列
	 * @param calssifyValue 分类列值
	 * @param tableName		拥有弹性域的表名
	 * @return
	 */
	List<MDTColumn> interpretFlexClassify(String calssifyValue, String tableName);
	
	/**
	 * 根据MDT弹性域取得纵向扩展
	 * @param mdtFieldId 弹性域ID	 
	 * * @return
	 */
	List<MDTVertical> getVerticalExt(long mdtFieldId);
}
