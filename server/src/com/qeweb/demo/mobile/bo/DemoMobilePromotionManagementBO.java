package com.qeweb.demo.mobile.bo;

import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * 促销执行管理
 */
public class DemoMobilePromotionManagementBO extends DemoMobileBaseBO {

	private static final long serialVersionUID = 8155718862370621457L;
	private String roadShowActivity;			// 路演推广活动
	private String closeTableActivity;			// 近台推广活动
	private String promotionActivity;			// 促销活动信息

	public DemoMobilePromotionManagementBO() {
		super();
		addBOP("roadShowActivity", new NotEmptyBop(1, 500));
		addBOP("closeTableActivity", new EmptyBop(500));
		addBOP("promotionActivity", new EmptyBop(500));
	}

	public String getPromotionActivity() {
		return promotionActivity;
	}

	public String getRoadShowActivity() {
		return roadShowActivity;
	}

	public void setRoadShowActivity(String roadShowActivity) {
		this.roadShowActivity = roadShowActivity;
	}

	public String getCloseTableActivity() {
		return closeTableActivity;
	}

	public void setCloseTableActivity(String closeTableActivity) {
		this.closeTableActivity = closeTableActivity;
	}

	public void setPromotionActivity(String promotionActivity) {
		this.promotionActivity = promotionActivity;
	}

}
