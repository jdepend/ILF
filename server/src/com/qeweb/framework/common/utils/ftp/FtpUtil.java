package com.qeweb.framework.common.utils.ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.file.FileUtil;

final public class FtpUtil {

	/**
	 * 根据ftp.properties配置向FTP服务器上传文件
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input  输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean upload(String filename, InputStream input) {
		return upload(FTPConfig.getHostName(), FTPConfig.getPort(),
				FTPConfig.getUserName(), FTPConfig.getPassword(),
				FTPConfig.getUploadPath(), filename, input);
	}
	
	/**
	 * 根据ftp.properties配置从FTP服务器下载文件   
	 * @param fileName 要下载的文件名   
	 * @return 成功返回true，否则返回false
	 */
	public static boolean download(String fileName) {
		return download(FTPConfig.getHostName(), FTPConfig.getPort(),
				FTPConfig.getUserName(), FTPConfig.getPassword(),
				FTPConfig.getUploadPath(), fileName, getDownloadocalPath());
	}
	
	/**
	 * 从FTP下载到本地的默认路径
	 * @return
	 */
	final public static String getDownloadocalPath() {
		return Envir.getHome() + FTPConfig.getDownloadPath();
	}
	
	/**
	 * Description: 向FTP服务器上传文件
	 * @param hostname  FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username  FTP登录账号
	 * @param password  FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input  输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean upload(String hostname, int port,
			String username, String password,
			String path, String filename, 
			InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器,如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(hostname, port);
			// 登录
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**   
     * Description: 从FTP服务器下载文件   
     * @Version1.0   
     * @param url FTP服务器hostname   
     * @param port FTP服务器端口   
     * @param username FTP登录账号   
     * @param password FTP登录密码   
     * @param remotePath FTP服务器上的相对路径   
     * @param fileName 要下载的文件名   
     * @param localPath 下载后保存到本地的路径   
     * @return   
     */    
	public static boolean download(String hostname, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(hostname, port);
			// 登录 ,如果采用默认端口,可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			// 转移到FTP服务器目录
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					if(!new File(localPath).exists()) {
						FileUtil.makedir(localPath);
					}
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	
	
	/**
	 * 将FTP服务器上文件下载到本地
	 * 
	 */
	public void testDownFile() {
		try {
			boolean flag = download("127.0.0.1", 21, "wangjia",
					"wangjia", "test", "test.txt", "D:/");
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testUpLoadFromString() {
		try {
			String str = "这是要写入的字符串！";
			InputStream input = new ByteArrayInputStream(str.getBytes("utf-8"));
			boolean flag = upload("127.0.0.1", 21, "wangjia",
					"wangjia", "test", "test.txt", input);
			System.out.println(flag);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		new FtpUtil().testUpLoadFromString();
//		
//		String str = "this is a test";
//		InputStream input = new ByteArrayInputStream(str.getBytes("utf-8"));
//		FtpUtil.upload("test2.txt", input);
//		
//		new FtpUtil().testDownFile();
		FtpUtil.download("purchase_order.xls");
	}
	
}
