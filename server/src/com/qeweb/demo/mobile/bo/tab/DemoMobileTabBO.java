package com.qeweb.demo.mobile.bo.tab;

import java.util.List;

import com.qeweb.demo.mobile.bop.tab.scoreBOP;
import com.qeweb.framework.bc.BusinessObject;

/**
 *	手机tab Demo
 */
public class DemoMobileTabBO extends BusinessObject {

	private static final long serialVersionUID = 5561447204026767829L;
	private String server_quality; //服务质量
	private String server_speed;// 服务速度
	private String server_satisfied;// 服务满意度
	private String surr_display; // 店面陈列
	private String surr_clean;// 店面清洁
	private String surr_poster;// 店面海报
	private String seller_num;// 人员数量充足
	private String seller_colth;// 人员着装

	public DemoMobileTabBO() {
		super();
		addBOP("server_quality", new scoreBOP());
		addBOP("server_speed", new scoreBOP());
		addBOP("server_satisfied", new scoreBOP());
		addBOP("surr_display", new scoreBOP());
		addBOP("surr_clean", new scoreBOP());
		addBOP("surr_poster", new scoreBOP());
		addBOP("seller_num", new scoreBOP());
		addBOP("seller_colth", new scoreBOP());
	}

	public void showAll(List<DemoMobileTabBO> boList) {
		for (DemoMobileTabBO bo : boList) {
			System.out.println("服务质量:" + bo.getServer_quality());
			System.out.println("服务速度、响应:" + bo.getServer_speed());
			System.out.println("服务满意度:" + bo.getServer_satisfied());
			System.out.println("店面陈列：" + bo.getSurr_display());
			System.out.println("店面清洁：" + bo.getSurr_clean());
			System.out.println("店面海报：" + bo.getSurr_poster());
			System.out.println("人员数量充足：" + bo.getSeller_num());
			System.out.println("人员着装统一：" + bo.getSeller_colth());
		}
	}

	public String getServer_quality() {
		return server_quality;
	}

	public void setServer_quality(String server_quality) {
		this.server_quality = server_quality;
	}

	public String getServer_speed() {
		return server_speed;
	}

	public void setServer_speed(String server_speed) {
		this.server_speed = server_speed;
	}

	public String getServer_satisfied() {
		return server_satisfied;
	}

	public void setServer_satisfied(String server_satisfied) {
		this.server_satisfied = server_satisfied;
	}

	public String getSurr_display() {
		return surr_display;
	}

	public void setSurr_display(String surr_display) {
		this.surr_display = surr_display;
	}

	public String getSurr_clean() {
		return surr_clean;
	}

	public void setSurr_clean(String surr_clean) {
		this.surr_clean = surr_clean;
	}

	public String getSurr_poster() {
		return surr_poster;
	}

	public void setSurr_poster(String surr_poster) {
		this.surr_poster = surr_poster;
	}

	public String getSeller_num() {
		return seller_num;
	}

	public void setSeller_num(String seller_num) {
		this.seller_num = seller_num;
	}

	public String getSeller_colth() {
		return seller_colth;
	}

	public void setSeller_colth(String seller_colth) {
		this.seller_colth = seller_colth;
	}
}
