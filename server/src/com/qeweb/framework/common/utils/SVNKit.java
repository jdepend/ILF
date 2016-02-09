package com.qeweb.framework.common.utils;

/**
 * SVN相关操作, SVN的相关配置在qewebProject.properties文件中,
 * 该类仅供开发期使用.
 */
public class SVNKit {

	/**
	 * 获取SVN当前版本号
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	final public static long getLatestRevision(String url, String userName, String password) {
//		/*
//	     * 对版本库进行初始化操作，使用https或http访问svn时，执行DAVRepositoryFactory.setup();
//	     * 对于通过使用svn:// 和 svn+xxx:// 访问svn时，执行SVNRepositoryFactoryImpl.setup();
//	     * 对于通过file:/// 访问svn的情况，执行 FSRepositoryFactory.setup();
//	     */
//		//某目录在svn的位置，获取目录对应的URL。即版本库对应的URL地址
//		try {
//			SVNURL svnUrl = SVNURL.parseURIEncoded(url);
//			//初始化
//			DAVRepositoryFactory.setup();   
//		    //提供认证
//			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password);
//
//			SVNRepository repos = SVNRepositoryFactory.create(svnUrl); 
//			//设置认证
//			repos.setAuthenticationManager(authManager);   
//			
//			return repos.getLatestRevision();
//		} catch (SVNException e) {
//			e.printStackTrace();
//		}
		
		return 0L;
	}
}
