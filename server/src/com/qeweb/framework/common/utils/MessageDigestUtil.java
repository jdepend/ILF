package com.qeweb.framework.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 * 密码加密类
 */
public class MessageDigestUtil {
	/**
	 * 密码加密
	 * @param dataToHash 密码
	 * @return 加密后的密码
	 */
	public static String encrypt(String dataToHash) {
		String tmp = null;
		if(dataToHash == null)
		    return tmp;
	    try {
	    	//安全哈希算法
	    	MessageDigest md = MessageDigest.getInstance("SHA1"); 
	    	byte[] byteTmpe = md.digest(dataToHash.getBytes());

	    	BASE64Encoder b64encoder = new BASE64Encoder();
	    	tmp = b64encoder.encode(byteTmpe);
	    }
	    catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    }
	    return tmp;
	}
}
