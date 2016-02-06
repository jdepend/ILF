package com.qeweb.framework.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;

public class QewebLogImpl implements IQewebLog {

	@Override
	public void errorLog(String logInfo, String context, Exception e) {
		if(StringUtils.isEmpty(logInfo) || StringUtils.isEmpty(context))
			return;
		String boName = StringUtils.split(context, ConstantSplit.SEMICOLON_SPLIT)[0];
		Log log = LogFactory.getLog(boName);
		if(e instanceof BOException){
			if(AppConfig.isDebug())
				log.error(logInfo, e);
			BOExceptionLog(log, (BOException) e);
		}
		else
			log.error(logInfo, e);
	}

	@Override
	public void log(String logInfo, String context) {
		if(StringUtils.isEmpty(logInfo) || StringUtils.isEmpty(context))
			return;
		String boName = StringUtils.split(context, ConstantSplit.SEMICOLON_SPLIT)[0];
		Log log = LogFactory.getLog(boName);
		log.info(logInfo);
	}

	private void BOExceptionLog(Log log, BOException e) {
		if(e.getBoException() != null){
			this.BOExceptionLog(log, e);
		}
		if(e.getQewebException() != null){
			log.error(e.getQewebException().getErrMsg(), e);
		}
	}

}
