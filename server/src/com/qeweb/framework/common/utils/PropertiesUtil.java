package com.qeweb.framework.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import com.qeweb.framework.common.utils.file.FileUtil;

/**
 * Properties文件相关操作类
 * 
 */
public class PropertiesUtil {

	/**
	 * 
	 * @param prop
	 * @param key
	 * @return 根据key值返回prop中对应的value信息.如果不存在对应的key, 则直接返回key的值.
	 */
	public static String getPropValue(Properties prop, String key) {
		if (prop == null)
			return null;
		
		return prop.containsKey(key) ? prop.getProperty(key) : key;
	}
	
	/**
	 * 根据路径加载.properties文件 
	 * @param path
	 * @return Properties
	 */
	public static Properties getPropertiesFile(String path) {
		Properties prop = new Properties();
		try {
			FileInputStream inputFile = new FileInputStream(getRealPath(path));
			prop.load(inputFile);
			inputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	/**
	 *
	 * @param path
	 * @return
	 * @throws URISyntaxException
	 */
	private static String getRealPath(String path) throws URISyntaxException {
		if(FileUtil.isFile(path))
			return path;

		URL url = PropertiesUtil.class.getResource(path);
		if(url == null)
			return "";
		else
			return url.toURI().getPath();
	}
	
	/**
	 * 将prop信息写入path指定的文件.
	 * @param path
	 * @param prop
	 */
	public static void save(String path, Properties prop) {
		try {
			if(!FileUtil.isFile(getRealPath(path)))
				return;
			
			OutputStream fos = new FileOutputStream(getRealPath(path));
			prop.store(fos, null);
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
