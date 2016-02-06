package com.qeweb.framework.pl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.font.FontUtil;

/**
 * PLWebHelper PL助手类
 */
public class PLWebHelper {
	
	/**
	 * 必填项标记.
	 * 如果Range的isRequired为true, 返回html格式的红色*号, 否则返回空串
	 * @param FinegrainedComponent 细粒度组件
	 * @return
	 */
	public static String requiredTextFlag(FinegrainedComponent fc){
		String requiredFlag = "<FONT color=red>*</FONT>";
		
		return fc.isRequired() ? requiredFlag : "";
		
	}
	
	/**
	 * 获取btns的总宽度，在table行级按钮中使用
	 * @param btns
	 * @return
	 */
	public static int getOperateWidth(List<CommandButton> btns) {
		int result = 0;
		for(CommandButton btn : btns) {
			if(btn.getWidth() > 0F)
				result += btn.getWidth();
			else
				result += FontUtil.getStrWidth(btn.getText());
		}
		
		return result;
	}
	
	/**
	 * copyProperties
	 * @param target
	 * @param source
	 */
	public static void copyViewComponent(ViewComponent target, ViewComponent source){
		try {
			PropertyUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		//有时会发生PropertyUtils.copyProperties(target, source) 不能复制source中类类型属性的情况,
		//此时需要特殊处理
		if(target.getBc() == null) {
			target.setBc(source.getBc());
			target.setPageContextInfo(source.getPageContextInfo());
			if(source instanceof CommandButton)
				((CommandButton) target).setParent(((CommandButton)source).getParent());
		}
	}
}
