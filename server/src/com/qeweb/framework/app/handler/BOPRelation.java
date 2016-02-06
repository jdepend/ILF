package com.qeweb.framework.app.handler;

import java.util.Map;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.exception.ExceptionUtil;
import com.qeweb.framework.manager.AppManager;

/**
 * FinegrainedSubmit处理细粒度组件提交，
 * 它为了将细粒度组件提交与struts action分离而建立。
 *
 */
public class BOPRelation {
	
	
	/**
	 * 处理Bop 关联。<br>
	 * finegrainedId代表的细粒度组件的改变将导致与之关联的细粒度组件产生变化。<br>
	 * 
	 * relationHandle首先从dataIsland中还原出finegrainedId绑定的BOP，
	 * 再执行BOP.relationHandle，
	 * 最后将结果回写到dataIsland。
	 * 
	 * @param finegrainedId 细粒度组件的Id
	 * @see com.qeweb.framework.pal.ViewComponent
	 * @param dataIsland 数据岛
	 * @return 
	 */
	final static public String bopRelationHandle(String finegrainedId, String dataIsland) {
		BOProperty sourceBop = AppManager.createDataIsland().revertBOPById(finegrainedId, dataIsland);
		
		try {
			Map<String, BusinessComponent> resultMap = sourceBop.bopRelationHandle(sourceBop);
			return AppManager.createDataIsland().updateFRelationDataIsland(dataIsland, resultMap);
		} catch(Exception e) {
			ExceptionUtil.handle(sourceBop, e);
		}
		
		return null;
	}
	
	/**
	 * 处理Bo 关联。<br>
	 * finegrainedId代表的细粒度组件的改变将导致与之关联的粗粒度组件内容产生变化。<br>
	 * 
	 * relationHandle首先从dataIsland中还原出finegrainedId绑定的BOP，
	 * 再执行BOP.relationHandle，
	 * 最后将结果回写到dataIsland。
	 * 
	 * @param finegrainedId 细粒度组件的Id
	 * @see com.qeweb.framework.pal.ViewComponent
	 * @param dataIsland 数据岛
	 * @return 
	 */
	final static public String boRelationHandle(String finegrainedId, String dataIsland) {
		BOProperty sourceBop = AppManager.createDataIsland().revertBOPById(finegrainedId, dataIsland);
		BusinessObject relationBo = AppManager.createDataIsland().revertRelationBO(dataIsland);
		try {
			Map<String, Object> resultMap = sourceBop.boRelationHandle(relationBo);
			return AppManager.createDataIsland().updateFCRelationDataIsland(dataIsland, resultMap);
		} catch(Exception e) {
			ExceptionUtil.handle(sourceBop, e);
		}
		
		return null;
	}
}
