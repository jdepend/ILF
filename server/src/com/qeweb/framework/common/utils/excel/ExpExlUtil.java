package com.qeweb.framework.common.utils.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.WebUtil;
import com.qeweb.framework.common.utils.file.FileUtil;

/**
 * ExpExlUtil
 * excel导出操作
 */
public class ExpExlUtil {

	private String userId;
	private String bussinessName;
	//每个excel容纳的最大数据量
	final static public int XLS_DATA_COUNT = BigExcelUtil.XLS_DATA_COUNT;
	
	public ExpExlUtil(){
		super();
	}
	
	/**
	 * ExpExlUtil构造函数， 通过当前用户id和业务名称创建文件名
	 * @param userId		当前用户名称
	 * @param bussinessName 业务名
	 */
	public ExpExlUtil(Long userId, String bussinessName) {
		this.userId = userId.toString();
		this.bussinessName = bussinessName;
		try {
			FileUtil.del(getTempDictionaryName());
			FileUtil.del(getZipDictionaryName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出操作， 导出单个excel时使用
	 * @param bos
	 * @param titles	标题列
	 */
	public boolean exportExcel(List<Object[]> bos, String[] titles) {
		HttpServletRequest request = Envir.getRequest();
		HttpServletResponse response = Envir.getResponse();
		WebUtil.prepareExcelExport(request, response, getXlsName());
		try {
			return BigExcelUtil.exportExcel(bos, titles, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 导出操作, 导出多个excel时循环调用该方法
	 * <br>
	 * 1、在服务器上创建一个目录  <br>
	 * 2、在目录中创建多个临时excel文件<br>
	 * 3、将excel文件打包成zip<br>
	 * @param bos 	
	 * @param titles
	 * @param exlSeq	excel的序号
	 */
	public boolean exportExcel(List<Object[]> bos, String[] titles, int exlSeq) {
		return BigExcelUtil.exportExcel(bos, titles, exlSeq, getTempExpFile());
	}
	
	/**
	 * 压缩excel, 压缩后删除源文件
	 * @param excelCount
	 */
	public boolean zipExcel(int excelCount) {
		if(excelCount > 1) {
			FileUtil.makedir(getZipDictionaryName());
			FileUtil.Zip(getTempDictionaryName(), getZipFileName());
			try {
				FileUtil.del(getTempDictionaryName());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 下载excel，下载后将源文件删除
	 * @return
	 */
	public boolean downLoad() {
		try {
			HttpServletResponse response = Envir.getResponse();
			response.reset(); 
			response.setHeader("Content-disposition ","attachment; filename=" + getSimpleFileName());   
		    response.setContentType("application/zip");
			boolean result = FileUtil.fileCopy(getZipFileName(), response.getOutputStream());
			FileUtil.del(getZipFileName());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 创建导出的excel文件
	 * 当需要导出多个excel是使用该方法
	 * @return
	 */
	private File getTempExpFile() {
		String dictionaryName = getTempDictionaryName();
		FileUtil.mkdirs(dictionaryName);
		
		return new File(dictionaryName + "/" + getXlsName());
	}
	
	
	/**
	 * 导出的路径：expTemp/excel/myExcel/userId
	 * @return
	 */
	private String getTempDictionaryName() {
		return getPublicDictionaryName() + "/myExcel/" + userId;
	}
	
	/**
	 * 压缩文件的路径：expTemp/excel/zip/userId
	 * @return
	 */
	private String getZipDictionaryName() {
		return getPublicDictionaryName() + "zip/" + userId;
	}
	
	/**
	 * 
	 * @return
	 */
	private String getPublicDictionaryName() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");    
		String path = url.getPath();   
		int index = path.lastIndexOf("WEB-INF/classes/");   

		if (index > 0) 
			path = path.substring(0, index);

		return path + "expTemp/excel";
	}
	
	
	private String getZipFileName() {
		return getZipDictionaryName() + "/" 
			+ bussinessName + DateUtils.getNowStr() + ".zip";
	}
	
	private String getSimpleFileName() {
		return bussinessName + ".xls";
	}
	
	private String getXlsName() {
		return bussinessName + DateUtils.getNowStr() + ".xls";
	}

	
	/**
	 * 导出操作， 导出单个excel时使用
	 * @param bos
	 * @param titles	标题列
	 * @param out		输出文件流
	 * @return
	 */
	public boolean exportExcel(List<Object[]> bos, String[] titles, FileOutputStream out) {
		return BigExcelUtil.exportExcel(bos, titles, out);
	}
}
