package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * 公告管理BO
 *
 */
public class DemoMobileMessageBO extends BusinessObject{

	private static final long serialVersionUID = 8450930305803347903L;
	
	private String messageTitle;		//标题
	private String messageContent;		//内容
	private Timestamp sendTime;			//发送时间
	
	public DemoMobileMessageBO() {
		super();
		addBOP("messageTitle", new NotEmptyBop(1, 64));
		addBOP("messageContent", new NotEmptyBop(1, 256));
	}
	
	@Override
	public void insert() throws Exception {
		this.setSendTime(DateUtils.getCurrentTimestamp());
		super.insert();
	}
	
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
}
