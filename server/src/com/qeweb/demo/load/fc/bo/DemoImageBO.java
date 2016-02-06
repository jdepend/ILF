package com.qeweb.demo.load.fc.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.load.fc.bop.DemoImageBOP_1;
import com.qeweb.demo.load.fc.bop.DemoImageBOP_2;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 按钮示例.
 * 路径: 装载-按钮
 */
public class DemoImageBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 906860936113266380L;
	
	private String image_1;
	private String image_2;
	private String image_3;
	private String image_4;
	private String s1;
	private String s2;
	private String s3;
	
	public DemoImageBO() {
		super();
		addBOP("image_1", new DemoImageBOP_1());
		addBOP("image_2", new DemoImageBOP_2());
		addBOP("image_3", new ImageBOP());
		addBOP("image_4", new ImageBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		List<DemoImageBO> boList = new LinkedList<DemoImageBO>();
		
		DemoImageBO bo1 = new DemoImageBO();
		bo1.setId(1);
		bo1.setImage_2("http://www.baidu.com/img/baidu_sylogo1.gif");
		//一个单元格中显示多个图片, 图片路径用逗号分隔
		bo1.setImage_3("http://www.baidu.com/img/baidu_sylogo1.gif,http://www.google.cn/landing/cnexp/google-search.png");
		bo1.setImage_4("http://www.google.cn/landing/cnexp/google-search.png");
		
		DemoImageBO bo2 = new DemoImageBO();
		bo2.setId(2);
		bo2.setImage_2("http://www.google.cn/landing/cnexp/google-search.png");
		bo2.setImage_3("http://www.baidu.com/img/baidu_sylogo1.gif");
		bo2.setImage_4("http://www.google.cn/landing/cnexp/google-search.png");
		
		DemoImageBO bo3 = new DemoImageBO();
		bo3.setId(3);
		bo3.setImage_2("http://www.baidu.com/img/baidu_sylogo1.gif");
		bo3.setImage_3("http://www.baidu.com/img/baidu_sylogo1.gif");
		bo3.setImage_4("http://www.google.cn/landing/cnexp/google-search.png");
		
		boList.add(bo1);
		boList.add(bo2);
		boList.add(bo3);
		
		Page page = new Page(boList, boList.size(), getPageSize(), start);
		//注意: 此处需要使用initPreferencePage将BO属性的值赋予属性对应的BOP
		initPreferencePage(page);

		return page;
	}
	
	public String getImage_1() {
		return image_1;
	}

	public void setImage_1(String image_1) {
		this.image_1 = image_1;
	}

	public String getImage_2() {
		return image_2;
	}

	public void setImage_2(String image_2) {
		this.image_2 = image_2;
	}

	public String getImage_3() {
		return image_3;
	}

	public void setImage_3(String image_3) {
		this.image_3 = image_3;
	}

	public String getImage_4() {
		return image_4;
	}

	public void setImage_4(String image_4) {
		this.image_4 = image_4;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public String getS3() {
		return s3;
	}

	public void setS3(String s3) {
		this.s3 = s3;
	}
}
