package com.qeweb.framework.bc.sysbop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;

public class FileBopUtil {
	private static final Log log = LogFactory.getLog(FileBOP.class);

	/**
	 * 执行file标签中operate属性所指定方法<br>	 
	 * @param bop
	 * @param operate
	 */
	@SuppressWarnings("deprecation")
	final public static boolean executeBOPMethod(FileBOP bop, String operate){
		
		Method method = getMethod(bop, operate);
		try {
			if(method != null){
				return (Boolean) method.invoke(bop);
			}
			else{
				bop.setErrorMessage(AppLocalization.getLocalization("file.upload.exception"));
				log.error("no method");
				return false;
			}
		} catch (Exception e) {
			log.error(e.toString());
			bop.setErrorMessage(AppLocalization.getLocalization("file.upload.exception"));
			return false;
		}
	}
	
	/**
	 * 获取FileBOP中指定方法名的无参方法<br>
	 * @param bop
	 * @param methodName
	 * @return FileBOP 的方法
	 */
	@SuppressWarnings("rawtypes")
	private static Method getMethod(FileBOP bop, String methodName){
		if(bop == null)
			return null;
		
		for (Method method : bop.getClass().getMethods()) {
			if(StringUtils.isNotEqual(methodName, method.getName()))
				continue;
			
			Class[] paramTypes = method.getParameterTypes();
			if(paramTypes.length != 0)
				continue;
			
			return method;
		}
		
		return null;
	}
	

	/**
	 * 获取FileBOP
	 * @return
	 * @throws IOException
	 */
	public static FileBOP getFileBop(BOProperty boproperty){
		BOProperty bop = BoOperateUtil.getRealBop(boproperty);	
		if(bop instanceof FileBOP)
			return (FileBOP) bop;
		else
			return null;
	}
	
	/**
	 * 获取MultiFileBOP
	 * @param boproperty
	 * @return
	 */
	public static MultiFileBOP getMultiFileBOP(BOProperty boproperty){		
		BOProperty bop = BoOperateUtil.getRealBop(boproperty);	
		if(bop instanceof MultiFileBOP)
			return (MultiFileBOP) bop;
		else
			return null;
	}

	/**
	 * 获取上传文件后缀名称
	 */
	public static String getExtention(String fileName) {
		int indexOf = fileName.lastIndexOf(ConstantSplit.BIND_SPLIT);
		if(indexOf > 0)
			return fileName.substring(indexOf);
		else
			return "";
	}
	
	/**
	 * 组装扩展名字符串
	 * @param typeSet
	 * @return
	 */
	public static String getTypeStr(Set<String> typeSet){
		StringBuffer sbr = new StringBuffer();
		for(String fileType : typeSet){
			sbr.append("\".");
			sbr.append(fileType);
			sbr.append("\",");
		}
		return StringUtils.removeEnd(sbr.toString());
	}
	

	
	/**
	 * 获得当前上传文件的存储总量
	 * @param uploadSize
	 * @return
	 */
	public static long getUploadSize(List<FileBOP> files) {
		long size = 0L;
		for(FileBOP bop : files){
			if(bop.getFile() == null)
				continue;
			size += bop.getFile().length();
		}
		return size;
	}
}
