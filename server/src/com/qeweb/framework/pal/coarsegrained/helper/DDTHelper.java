package com.qeweb.framework.pal.coarsegrained.helper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.impconfig.ddt.use.DDTSchemaHandler;
import com.qeweb.framework.impconfig.ddt.use.bean.DDTSchema;
import com.qeweb.framework.impconfig.mdt.use.MDTHanlder;
import com.qeweb.framework.manager.VCManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 粗粒度组件的DDT助手类
 */
public class DDTHelper {
	
	/**
	 * 获取DDT中配置的细粒度组件
	 * @param container
	 * @return
	 */
	final static public Map<String, FinegrainedComponent> getDDTFC(Container container) {
		//DDT方案中的细粒度组件, 这些细粒度组件绑定弹性域, 
		List<DDTSchema> ddtFcList = DDTSchemaHandler.getDDTFC(Envir.getRequestURI(), container.getId());
		if(ContainerUtil.isNull(ddtFcList))
			return null;
		
		Map<String, FinegrainedComponent> result = new LinkedHashMap<String, FinegrainedComponent>();
		//静态域, 通过标签加载的细粒度组件, 这些细粒度组件不可在DDT中配置.
		Map<String, FinegrainedComponent> staticFCList = container.getFcList();
		for(DDTSchema ddtFC : ddtFcList) {
			if(staticFCList.containsKey(ddtFC.getBopName()))
				continue;
			
			FinegrainedComponent fc = (FinegrainedComponent)VCManager.getVCFactory().createVC(ddtFC.getType());
			fc.setBcId(ddtFC.getBopName());
			fc.setParent(container);
			if(fc.getBc() == null)
				continue;
			
			if(StringUtils.isNotEmpty(ddtFC.getContextName()))
				fc.setLocalName(ddtFC.getContextName());
			fc.setName(VCUtil.createFinegrainedName(container.getName(), ddtFC.getBopName()));
			fc.setId(VCUtil.createFinegrainedID(container.getName(), ddtFC.getBopName()));
			if(StringUtils.isNotEmpty(ddtFC.getAlias()))
				fc.setText(ddtFC.getAlias());
			fc.setPrevBCID(ddtFC.getPrevBopName());
			fc.setPageContextInfo(container.getPageContextInfo());
			//设置DDT范围
			MDTHanlder.setRange(fc, ddtFC);
			
			result.put(ddtFC.getBopName(), (FinegrainedComponent)fc);
		}
		
		return result;
	}

	/**
	 * 将静态细粒度组件和动态细粒度组件合并并排序, 每个细粒度组件都有指向前一个组件的标识(prevBopName), 根据该标识排序.
	 * <ul>getSortFCList使用如下方法排序:
	 * <li>1.将staticFCList转换为LinkedList;
	 * <li>2.将dynamicFCList中所有无需排序的组件(即getPrevBCID为空的组件)直接加入result末尾,并将无需排序的组件从dynamicFCList中删除;
	 * <li>3.如果dynamicFCList中有需要排序的组件X,且X的PrevBCID能够指向result中的组件Y, 则X插入Y前, 并从dynamicFCList中删除X;
	 * <li>4.如果遍历一次dynamicFCList后没有发现任何组件能够指向result中的组件, 则将dynamicFCList的所有组件全部添加到result末尾.
	 * @param staticFCList	将静态细粒度组件和动态细粒度组件合并并排序
	 * @param dynamicList 	动态细粒度组件 -> 通过DDT schema加载的细粒度组件
	 * @return
	 */
	final static public Map<String, FinegrainedComponent> getSortFCList(
			Map<String, FinegrainedComponent> staticFCList, Map<String, FinegrainedComponent> dynamicFCList) {
		//1.将staticFCList转换为LinkedList
		List<FinegrainedComponent> result = new LinkedList<FinegrainedComponent>();
		for(Entry<String, FinegrainedComponent> entry : staticFCList.entrySet()) {
			result.add(entry.getValue());
		}
		
		//2. 将dynamicFCList中所有无需排序的组件(即getPrevBCID为空的组件)直接加入result末尾,
		//并将无需排序的组件从dynamicFCList中删除.
		Iterator<String> dynamicFCItr = dynamicFCList.keySet().iterator();
		while(dynamicFCItr.hasNext()) {
			String bcId = dynamicFCItr.next();
			FinegrainedComponent dynamicFC = dynamicFCList.get(bcId);
			if(StringUtils.isEmptyStr(dynamicFC.getPrevBCID())) {
				result.add(dynamicFC);
				dynamicFCList.remove(bcId);
				dynamicFCItr = dynamicFCList.keySet().iterator();
			}
		}
		
		//3. 如果dynamicFCList中有需要排序的组件X,且X的PrevBCID能够指向result中的组件Y, 
		//则X插入Y前, 并从dynamicFCList中删除X.
		dynamicFCItr = dynamicFCList.keySet().iterator();
		while(dynamicFCItr.hasNext()) {
			String bcId = dynamicFCItr.next();
			FinegrainedComponent dynamicFC = dynamicFCList.get(bcId);
			int idx = indexOf(result, dynamicFC.getPrevBCID());
			if(idx != -1) {
				result.add(idx, dynamicFC);
				dynamicFCList.remove(bcId);
				dynamicFCItr = dynamicFCList.keySet().iterator();
			}
		}
		
		Map<String, FinegrainedComponent> sortResult = new LinkedHashMap<String, FinegrainedComponent>();
		for(FinegrainedComponent fc : result) {
			sortResult.put(fc.getBcId(), fc);
		}
		
		//4.如果遍历一次dynamicFCList后没有发现任何组件能够指向result中的组件, 
		//则将dynamicFCList的所有组件全部添加到result末尾
		if(!dynamicFCList.isEmpty())
			sortResult.putAll(dynamicFCList);
		
		return sortResult;
	}
	
	private static int indexOf(List<FinegrainedComponent> fcList, String bcId) {
		for(int i = 0; i < fcList.size(); i++) {
			if(StringUtils.isEqual(fcList.get(i).getBcId(), bcId))
				return i;
		}
		
		return -1;
	}
}
