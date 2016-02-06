package com.qeweb.framework.impconfig.mdt.use;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.impconfig.mdt.use.bean.MDTColumn;
import com.qeweb.framework.impconfig.mdt.use.dao.ia.IMDT_HandlerDao;
import com.qeweb.framework.impconfig.mdt.use.dao.impl.MDT_HandlerDaoImpl;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;


/**
 * 弹性域处理者， 负责解析弹性域相关信息
 */
public class MDTHanlder {
	
	private static IMDT_HandlerDao mdtHandlerDao = new MDT_HandlerDaoImpl();
	
	/**
	 * 为fc设置范围.
	 * <p>1.通过字段类型(string, int, date...)设置范围;</p>
	 * <p>2.通过范围描述设置范围;</p>
	 * <p>3.通过弹性域值集(数据字典)设置范围;</p>
	 * <p>4.通过弹性域的多段值集设置范围.</p>
	 * @param fc
	 * @param ddtFC
	 */
	final public static void setRange(FinegrainedComponent fc, DDTSchema ddtFC) {
		MDTValueSetHandler.setRange(fc, ddtFC);
	}
	
	/**
	 * 解析弹性域，将弹性域的正确解释设置到bo
	 * @param bo
	 * @param calssifyValue 分类列值， 如果calssifyValue为空，则仅解析固定列
	 * @param tableName		拥有弹性域的表名
	 * @return
	 */
	static public BusinessObject interpretFlex(BusinessObject bo, String calssifyValue, String tableName) {
		List<MDTColumn> mdtList = new LinkedList<MDTColumn>();
		
		//解析弹性域固定列
		ContainerUtil.addAll(mdtList, mdtHandlerDao.interpretFlexFixed(tableName));
		//如果calssifyValue不为空，还将解析弹性域分类列
		if(StringUtils.isNotEmpty(calssifyValue)) 
			ContainerUtil.addAll(mdtList, mdtHandlerDao.interpretFlexClassify(calssifyValue, tableName));
		
		//为弹性域设置具体含义
		for(MDTColumn mdtColumn : mdtList) {
			BOProperty bop = bo.getBOP(mdtColumn.getName());
			if(bop == null)
				continue;
			
			bop.setLocalName(mdtColumn.getAlias());
		}
		
		return bo;
	}
}
