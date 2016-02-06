package com.qeweb.framework.common.constant;

/**
 * mail.properties 相关信息
 * 用于邮件配置
 */
public interface ConstantMailProp {

	final public static String PROPFILE_APP = "/prop/mail.properties";
	// 发送邮件的服务器的IP
	public static final String MAIL_SERVER_HOST = "mailServerHost";
	// 发送邮件的服务器的端口
	public static final String MAIL_SERVER_PORT = "mailServerPort";
	// 登陆邮件发送服务器的用户名
	public static final String MAIL_USERNAME = "mailUserName";
	// 登陆邮件发送服务器的密码
	public static final String MAIL_PWD = "mailPwd";
	// 是否需要身份验证
	public static final String MAIL_VALIDATE = "mailValidate";
}
