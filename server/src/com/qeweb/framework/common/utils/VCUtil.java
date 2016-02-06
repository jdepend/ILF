package com.qeweb.framework.common.utils;

import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.pal.ViewComponent;

/**
 * @see ViewComponent
 */
public class VCUtil {

	/**
	 * 创建粗粒度组件Name
	 * @param containerTagId 页面元素的id属性
	 * @param bind
	 * @return 粗粒度组件Name
	 */
	final static public String createContainerName(String containerTagId, String bind) {
		return containerTagId + ConstantDataIsland.HORIZONTAL_SPLIT + bind;
	}

	/**
	 * 创建细粒度组件ID
	 * @param containerName
	 * @param bind
	 * @return 细粒度组件ID
	 */
	final static public String createFinegrainedID(String containerName, String bind) {
		return containerName + ConstantDataIsland.HORIZONTAL_SPLIT + bind;
	}

	/**
	 * 创建细粒度组件Name
	 * @param containerName
	 * @param bind
	 * @return 细粒度组件name
	 */
	final static public String createFinegrainedName(String containerName, String bind) {
		return containerName + ConstantDataIsland.HORIZONTAL_SPLIT + bind;
	}

	/**
	 * 创建控制组件的Name
	 * @param containerTagId
	 * @param bind
	 * @param operateTagName
	 * @return
	 */
	final static public String createOperateName(String containerTagId, String bind, String operateTagName) {
		return containerTagId + ConstantDataIsland.HORIZONTAL_SPLIT + bind + ConstantDataIsland.HORIZONTAL_SPLIT + operateTagName;
	}

	/**
	 * 创建page级控制组件的Name
	 * @param containerTagId
	 * @param bind
	 * @param operateTagName
	 * @return
	 */
	final static public String createPageOperateName(String bind, String operateTagName) {
		return bind + ConstantDataIsland.HORIZONTAL_SPLIT + operateTagName;
	}

	/**
	 * 根据细粒度组件ID(containerId-containerBind-bopbind)取得bopId;
	 * 如果细粒度组件在table弹出框中, 则细粒度组件ID: containerId-containerBind-bopbind-operate
	 * @param fcId  细粒度组件ID
	 * @return bop bind
	 */
	final static public String getBopBind(String fcId) {
		String[] arr = StringUtils.split(fcId, ConstantDataIsland.HORIZONTAL_SPLIT);
		String bind = arr[2];
		if(arr.length > 3){
			bind = arr[arr.length - 1];
		}
		return bind;
	}

	/**
	 * 根据containerName(id-bind)取得数据岛中BO的唯一标识(id)
	 * @param name
	 * @return id
	 */
	final static public String getDataIslandBOId(String containerName) {
		String[] arr = StringUtils.split(containerName, ConstantDataIsland.HORIZONTAL_SPLIT);
		if(StringUtils.isNotEmpty(arr)) {
			//普通粗粒度组件
			if(arr.length == 2)
				return arr[0];
			//表格弹出框的粗粒度组件, 如果有<qeweb:table id='tid' bind='bo'>, 
			//则其新增弹出框的containerName为: tid-bo-insert_form-bo;
			//修改弹出框和查看弹出框的containerName分别为: tid-bo-update_form-bo, tid-bo-view_form-bo;
			else if(arr.length == 4)
				return containerName;
			else 
				return null;
		}
		else
			return null;
	}

	/**
	 * 根据containerName(id-bind)或bind(是page控件时)取得bind
	 * @param name
	 * @return bind
	 */
	final static public String getBoBind(String containerName) {
		String[] arr = StringUtils.split(containerName, ConstantDataIsland.HORIZONTAL_SPLIT);
		if(StringUtils.isEmpty(arr))
			return null;

		return arr.length == 1 ? arr[0] : arr[1];
	}

	/**
	 * 获取buttonTag的name属性
	 * @param fcName
	 * @return
	 */
	final static public String getButtonTagName(String buttonName) {
		String[] result = buttonName.split(ConstantDataIsland.HORIZONTAL_SPLIT);
		return result[result.length - 1];
	}

	/**
	 * 根据组件的Id获得粗粒度组件的页面唯一标识Id
	 * @param vcId
	 * @return containerId
	 */
	final static public String getContainerTagId(String vcId){
		return vcId.split(ConstantDataIsland.HORIZONTAL_SPLIT)[0];
	}
}
