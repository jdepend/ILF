package com.qeweb.framework.pal.coarsegrained.helper;

import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.container.SimpleContainer;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.control.SimpleBtn;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.simplevc.finegrained.SimpleFC;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * MDT帮助类
 */
public class MDTHelper {
	
	/**
	 * 变量影响表单中的控制组件状态
	 * @param btnList
	 * @param simpleContainer
	 * @param vcId
	 * @param bcId
	 */
	final static public void var2Btn(Map<String, CommandButton> btnList, SimpleContainer simpleContainer, String vcId, String bcId) {
		Map<String, SimpleBtn> simpleBtnList = simpleContainer.getBtnList();
		if (ContainerUtil.isNull(simpleBtnList) || ContainerUtil.isNull(btnList))
			return;
		
		for (Entry<String, SimpleBtn> entry : simpleBtnList.entrySet()) {
			String btnName = VCUtil.createOperateName(vcId, bcId, entry.getKey());
			if (!btnList.containsKey(btnName))
				continue;
			OperateBOP bop = btnList.get(btnName).getBc();
			if (bop == null)
				continue;

			bop.getStatus().setDisable(entry.getValue().isDisable());
			bop.getStatus().setHidden(entry.getValue().isHidden());
			bop.getStatus().setReadonly(entry.getValue().isReadonly());
		}
	}

	/**
	 * 变量影响表单中的细粒度组件的值、状态
	 * @param fcList
	 * @param simpleContainer
	 */
	final static public void var2FC(Map<String, FinegrainedComponent> fcList, SimpleContainer simpleContainer) {
		Map<String, SimpleFC> simpleFCList = simpleContainer.getFcList();
		if (ContainerUtil.isNull(simpleFCList) || ContainerUtil.isNull(fcList))
			return;

		for (Entry<String, SimpleFC> entry : simpleFCList.entrySet()) {
			if (!fcList.containsKey(entry.getKey()))
				continue;
			BOProperty bop = fcList.get(entry.getKey()).getBc();
			if (bop == null)
				continue;

			if(entry.getValue().isDisable() != null)
				bop.getStatus().setDisable(entry.getValue().isDisable());
			if(entry.getValue().isHidden() != null)
				bop.getStatus().setHidden(entry.getValue().isHidden());
			if(entry.getValue().isReadonly() != null)
				bop.getStatus().setReadonly(entry.getValue().isReadonly());
			if(StringUtils.isNotEmpty(entry.getValue().getValueStr()))
				bop.setValue(entry.getValue().getValueStr());
		}
	}
}
