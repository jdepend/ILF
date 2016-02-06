package com.qeweb.framework.app.permissions;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.Buttons;
import com.qeweb.sysmanage.purview.bo.OperateBO;

/**
 * 检查权限的操作级权限
 */
public class CheckOptPermissionsImpl implements ICheckOptPermissions {

	/**
	 * 判断是否展示该按钮，如果能够展示（即有操作级权限），返回true
	 * @param btnName
	 * @param sourcePage
	 * @return
	 */
	@Override
	public boolean hasOperatePermission(String btnName, String sourcePage) {
		Buttons button = getButton(btnName, sourcePage);
		//如果一个按钮不参与配置, 则该按钮无需权限控制
		if(button == null)
			return true;

		Set<String> btnSet = UserContext.getBtnIds();
		if(ContainerUtil.isNull(btnSet))
			return false;
		
		return btnSet.contains(button.getId() + "");
	}

	
	/**
	 * 根据btnName和sourcePage判断是否在数据库中配置了按钮(该按钮需要控制视图级权限),
	 * 如果配置了,返回btnName和sourcePage指向的按钮,否则返回null.
	 * @param btnName
	 * @param sourcePage
	 * @return
	 */
	private Buttons getButton(String btnName, String sourcePage) {
		//取得所有能够分配权限的按钮([qeweb_sys_buttons]表中的所有数据)
		List<Buttons> allBtns = new OperateBO().getBtnList();
		if(ContainerUtil.isNull(allBtns))
			return null;
		
		for(Buttons button : allBtns) {
			//页面标识和按钮标识可以确定唯一一条记录
			if(StringUtils.isEqual(btnName, button.getBtnName()) 
					&& StringUtils.isEqual(sourcePage, button.getSourcePage()))
				return button;
		}
		
		return null;
	}
}