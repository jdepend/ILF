package com.qeweb.busplatform.common.constants;

import java.io.File;

import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.common.utils.ftp.FtpUtil;

/**
 * FTP上待导入的文件信息
 */
final public class ConstantFtpFiles {
	
	//采购订单excel
	final public static String PURCHASE_ORDER = "purchase_order.xls";

	/**
	 * 采购订单excel
	 * @return
	 */
	final public static FileBOP getPurchaseOrderFile() {
		FileBOP fileBOP = new FileBOP();
		File file = new File(FtpUtil.getDownloadocalPath() + PURCHASE_ORDER);
		
		//如果服务器上没有PURCHASE_ORDER文件，则重新从ftp下载
		if(!file.exists()) {
			FtpUtil.download(PURCHASE_ORDER);
			file = new File(FtpUtil.getDownloadocalPath() + PURCHASE_ORDER);
		}
		
		fileBOP.setFile(file);
		return fileBOP;
	}
}
