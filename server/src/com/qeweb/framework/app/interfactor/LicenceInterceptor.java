package com.qeweb.framework.app.interfactor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.licence.Licence;
import com.qeweb.framework.licence.command.LicenceCommandImpl;
import com.qeweb.framework.licence.util.SysMacUtil;

/**
 * 处理licence授权(仅发布版需要检验)
 * <br>
 * 以下几种情况说明不符合licence校验:
 * <li>1.licence文件不存在;
 * <li>2.系统安装时间/使用时限/最后登录日期 中任意一项为空;
 * <li>3.licence过期(当前日期超过 系统安装日期 + 使用时限);
 * <li>4.用户修改了系统时间为过去时(当前日期小于最后登录日期).
 * <br>
 * 当用户登录成功后, 修改最后登录日期.
 *
 */
public class LicenceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7052205781165658722L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		//验证licence
		Licence licence = getCurrentLienceInfo();
		if(licence == null || StringUtils.isEmpty(licence.getSetupDateStr())
				|| StringUtils.isEmpty(licence.getTimeLimitStr())
				|| StringUtils.isEmpty(licence.getLastLoginDateStr())
				|| !licence.isContinue()) {
			return "licenceError";
		}
		//将当前登录时间信息记录进licence文件
		licence.setLastLoginDateStr(DateUtils.dateToString(new Date()));
		new LicenceCommandImpl().updateLicenceFile(licence);

		return invocation.invoke();
	}

	/**
	 * 获取licence信息
	 * @return
	 */
	private Licence getCurrentLienceInfo() {
		String licenceFilePath = Envir.getContext().getRealPath("/") + "licence.txt";
		File licenceFile = new File(licenceFilePath);
		if(!licenceFile.exists()){
			return null;
		}
		List<String> licenceInfo = new ArrayList<String>();
		try{
			BufferedReader input = new BufferedReader(new FileReader(licenceFile));
			String text;
			while ((text = input.readLine()) != null) {
				licenceInfo.add(text);
			}
			Licence licence = new Licence();
			licence.setCurrSysMacStr(SysMacUtil.getMac());
			if(ContainerUtil.isNotNull(licenceInfo)) {
				licence.setProjectName(licenceInfo.get(0));
				if(licenceInfo.size() >= 2)
					licence.setSetupDateStr(licenceInfo.get(1));
				if(licenceInfo.size() >= 3)
					licence.setTimeLimitStr(licenceInfo.get(2));
				if(licenceInfo.size() >= 4)
					licence.setLastLoginDateStr(licenceInfo.get(3));
				if(licenceInfo.size() >= 5)
					licence.setSysMacStr(licenceInfo.get(4));
			}
			licence.setLicencePath(licenceFile.getPath());
			licence.setCurrentDate(DateUtils.getCurrentTimestamp());
			input.close();
			return licence;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
