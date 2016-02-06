package com.qeweb.framework.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

import com.qeweb.framework.app.action.FileHandleHelp;

/**
 * base64Code文件上传。
 */
public class Base64Upload {

	/**
	 * base64Code文件上传，主要用于终端文件上传。
	 * <br>
	 * 终端文件上传是在终端先将文件转换成base64编码，再把编码上传，
	 * upload负责将编码重新解析成文件。
	 * @param base64Code base64编码
	 * @param fileName	文件名称
	 * @return
	 */
	public static String upload(String base64Code, String fileName){
		if(StringUtils.isEmpty(base64Code) || StringUtils.isEmpty(fileName)){
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder
					.decodeBuffer(base64Code.replaceAll(" ", "+"));
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			File savefile = new File(FileHandleHelp.makdSaveDir(), FileHandleHelp.getFileSaveName(fileName));
			OutputStream out = new FileOutputStream(savefile);
			out.write(bytes);
			out.flush();
			out.close();
			return FileHandleHelp.getFileURLPath(savefile.getName());
		} catch (Exception e) {
		}
		
		return null;
	}
}
