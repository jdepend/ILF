package com.qeweb.busplatform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.common.utils.DateUtils;

/**
 * 接口下载日志，默认处理类
 */
public class InterfaceLog {

	/**
	 *
	 * @param logBuf 日志内容
	 * @param logDir 日志存放目录
	 * @throws IOException
	 */
	public static void saveLog(StringBuffer logBuf, String logDir) throws IOException {
		if (logBuf != null && logBuf.length() > 0) {
			File saveLog = FileHandleHelp.makdSaveDir(logDir);
			//保存日志名称
			String logName = DateUtils.dateToString(new Date(), "yyyy-mm-dd-hh-mm-ss") + ".txt";
			FileWriter fw = new FileWriter(saveLog.getPath() + "/" + logName);
			fw.write(logBuf.toString(),0,logBuf.length());
			fw.flush();
			fw.close();
		}
	}
}
