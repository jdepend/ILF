package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * 驻店员在岗管理
 */
public class DemoMobileSalesclerkWorkplaceBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = -6373362477710559326L;
	private String serviceIdea;			//服务理念
	private String corporateCulture;	//企业文化培训
	private String promotionActivity;	//促销活动策划

	public DemoMobileSalesclerkWorkplaceBO() {
		super();
		addBOP("serviceIdea", new NotEmptyBop(1, 500));
		addBOP("corporateCulture", new EmptyBop(500));
		addBOP("promotionActivity", new EmptyBop(500));
	}

	public String getServiceIdea() {
		return serviceIdea;
	}

	public void setServiceIdea(String serviceIdea) {
		this.serviceIdea = serviceIdea;
	}

	public String getCorporateCulture() {
		return corporateCulture;
	}

	public void setCorporateCulture(String corporateCulture) {
		this.corporateCulture = corporateCulture;
	}

	public String getPromotionActivity() {
		return promotionActivity;
	}

	public void setPromotionActivity(String promotionActivity) {
		this.promotionActivity = promotionActivity;
	}
}
