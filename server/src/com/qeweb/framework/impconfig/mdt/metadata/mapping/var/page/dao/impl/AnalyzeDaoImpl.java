package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.common.util.AnalyzeJspUtil;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo.AnalyzeComponentBO;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IAnalyzeDao;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pal.finegrained.enumcomp.OptionTranserSelect;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pal.finegrained.text.TextField;

/**
 * 配置变量与页面映射时用于解析jsp
 *
 */
public class AnalyzeDaoImpl extends XmlDaoImpl implements IAnalyzeDao {
	private static final long serialVersionUID = -401517416676647908L;

	public List<AnalyzeComponentBO> analyzeContainer(String url) {
		List<AnalyzeComponentBO> containerBOs = new ArrayList<AnalyzeComponentBO>();
		if(StringUtils.isEmpty(url))
			return containerBOs;
		
		//读取JSP内容
		String jspContent = FileUtil.readFile(Envir.getContext().getRealPath("/") + url);
		List<Container> list = AnalyzeJspUtil.getContainers(jspContent, null, false);
		
		if(ContainerUtil.isNull(list))
			return containerBOs;
		
		int i = 0;
		for (Container con : list) {
		    AnalyzeComponentBO bo = new AnalyzeComponentBO();
			bo.setId(i++);
			bo.setPageURL(url);
			bo.setVcId(con.getId());
			bo.setBind(con.getBcId());
			if (con instanceof Form) {
				bo.setVcType(VCType.VC_TYPE_FORM);
			}
			else if (con instanceof Table) {
				bo.setVcType(VCType.VC_TYPE_TABLE);
			}
			else if (con instanceof Tab) {
				bo.setVcType(VCType.VC_TYPE_TAB);
			}

			containerBOs.add(bo);
		}
		
		return containerBOs;
	}

	public List<AnalyzeComponentBO> analyzeComponent(String url,
			String containerId) {
		List<AnalyzeComponentBO> componentBOs = new ArrayList<AnalyzeComponentBO>();
		
		//读取JSP内容
		String jspContent = FileUtil.readFile(Envir.getContext().getRealPath("/") + url);
		Container container = AnalyzeJspUtil.getContainer(jspContent, containerId, null);
		if(container == null)
			return componentBOs;

		int i = 1;
		//粗粒度组件本身
		AnalyzeComponentBO con = new AnalyzeComponentBO();
		con.setId(i++);
		con.setBind(container.getBcId());
		con.setVcId(container.getId());
		setType(con, container);
		componentBOs.add(con);
		
		//细粒度组件&按钮组件
		for(ViewComponent vc : container.getVcList().values()){
			AnalyzeComponentBO bo = new AnalyzeComponentBO();
			bo.setId(i++);
	        con.setVcId(vc.getId());
			if(vc instanceof FinegrainedComponent){
				bo.setBind(vc.getBcId());
			}
			else if(vc instanceof CommandButton){
				bo.setBind(StringUtils.split(vc.getName(), "-")[2]);
			}
			setType(bo,vc);
			componentBOs.add(bo);
		}
		return componentBOs;
	}

	private void setType(AnalyzeComponentBO bo, ViewComponent vc) {
		if(vc instanceof Form){
			bo.setVcType(VCType.VC_TYPE_FORM);
		}
		else if(vc instanceof Table){
			bo.setVcType(VCType.VC_TYPE_TABLE);
		}
		else if(vc instanceof Tab){
			bo.setVcType(VCType.VC_TYPE_TAB);
		}
		else if(vc instanceof TextField){
			bo.setVcType(VCType.VC_TYPE_TEXTFEILD);
		}
		else if(vc instanceof TextArea){
			bo.setVcType(VCType.VC_TYPE_TEXTAREA);
		}
		else if(vc instanceof Label){
			bo.setVcType(VCType.VC_TYPE_LABEL);
		}
		else if(vc instanceof Password){
			bo.setVcType(VCType.VC_TYPE_PASSWORD);
		}
		else if(vc instanceof Hidden){
			bo.setVcType(VCType.VC_TYPE_HIDDEN);
		}
		else if(vc instanceof Select){
			bo.setVcType(VCType.VC_TYPE_SELECT);
		}
		else if(vc instanceof Radio){
			bo.setVcType(VCType.VC_TYPE_RADIO);
		}
		else if(vc instanceof CheckBox){
			bo.setVcType(VCType.VC_TYPE_CHECKBOX);
		}
		else if(vc instanceof OptionTranserSelect){
			bo.setVcType(VCType.VC_TYPE_OPTIONTRANSERSELECT);
		}
		else if(vc instanceof Anchor){
			bo.setVcType(VCType.VC_TYPE_ANCHOR);
		}
		else if(vc instanceof CommandButton){
			bo.setVcType(VCType.VC_TYPE_BTN);
		}
	}
}
