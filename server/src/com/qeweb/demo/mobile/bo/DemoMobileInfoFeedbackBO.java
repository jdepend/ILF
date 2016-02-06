package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;


/**
 * 市场信息反馈
 */
public class DemoMobileInfoFeedbackBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -4968291536414142084L;
	private String competitiveInfo;			// 竞品信息采集
	private String marketInfo;				// 市场信息反馈
	private String priceFeedback;			// 产品价格反馈

	public DemoMobileInfoFeedbackBO() {
		super();
		addBOP("competitiveInfo", new NotEmptyBop(1, 100));
		addBOP("marketInfo", new EmptyBop(100));
		addBOP("priceFeedback", new EmptyBop(100));
	}

	public String getCompetitiveInfo() {
		return competitiveInfo;
	}

	public void setCompetitiveInfo(String competitiveInfo) {
		this.competitiveInfo = competitiveInfo;
	}

	public String getMarketInfo() {
		return marketInfo;
	}

	public void setMarketInfo(String marketInfo) {
		this.marketInfo = marketInfo;
	}

	public String getPriceFeedback() {
		return priceFeedback;
	}

	public void setPriceFeedback(String priceFeedback) {
		this.priceFeedback = priceFeedback;
	}

}
