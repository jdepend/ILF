package com.qeweb.demo.load.fc.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.load.fc.bop.DemoAnchorBOP;
import com.qeweb.demo.load.fc.bop.DemoDownloadMFileBOP;
import com.qeweb.demo.load.fc.bop.DemoDownloadSFileBOP;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.Page;

/**
 * demo: 上传和下载示例.
 * 路径: 装载-上传和下载
 */
public class DemoDownloadBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4190688190784360008L;

	private MultiFileBOP multiFile;				//多文件
	private FileBOP downloadSFile_1;			//单文件下载1
	private FileBOP downloadSFile_2;			//单文件下载2
	private String downloadSFile_3;				//单文件下载3
	private String downloadSFile_4;				//单文件下载4
	private MultiFileBOP downloadMFile;			//多文件下载

	
	public DemoDownloadBO() {
		super();
		addBOP("downloadSFile_1", new DemoDownloadSFileBOP());
		addBOP("downloadSFile_2", new DemoDownloadSFileBOP());
		getBOP("downloadSFile_3").setValue("http://apache.etoak.com/ant/binaries/apache-ant-1.8.2-bin.zip");
		addBOP("downloadSFile_4", new DemoAnchorBOP());
		addBOP("downloadMFile", new DemoDownloadMFileBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		List<DemoDownloadBO> boList = new LinkedList<DemoDownloadBO>();
		
		DemoDownloadBO bo1 = new DemoDownloadBO();
		bo1.setId(1);
		/*
		 * 注意: DemoAnchorBOP的init中设置了超链接的href和展示值, 由于表格的record不会再次执行bop.init(), 
		 * 所以此处需要手动执行bo.init(), 它将循环执行每个bop.init();
		 * singleFileBop1 没有设置init方法, 需要重新赋值.
		 */
		bo1.init();
		bo1.getBOP("downloadSFile_3").setValue("http://apache.etoak.com/ant/binaries/apache-ant-1.8.2-bin.zip");
		boList.add(bo1);
		
		DemoDownloadBO bo2 = new DemoDownloadBO();
		bo2.setId(2);
		bo2.getBOP("downloadSFile_3").setValue("http://download.eclipse.org/vjet/updates-0.10/");
		bo2.init();
		boList.add(bo2);
		
		Page page = new Page(boList, boList.size(), getPageSize(), start);
		initPreferencePage(page);

		return page;
	}
	
	/**
	 * 保存
	 * @param boList
	 */
	public void save(List<DemoDownloadBO> boList) {
		System.out.println("----------------------------------------------------------save------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t");
		for (DemoDownloadBO bo : boList) {
			System.out.println("|\tdownloadSFile_1 = " + bo.getDownloadSFile_1().getPath() + 
					"\tdownloadSFile_2 = " +  bo.getDownloadSFile_2().getPath() +
					"\tdownloadSFile_3 = " +  bo.getDownloadSFile_3() +
					"\tdownloadSFile_4 = " +  bo.getDownloadSFile_4() +
					"\tdownloadMFile = " + bo.getDownloadMFile().getValue().getValue());
		}
		System.out.println("----------------------------------------------------------end ------------------------------------------------------");
	}

	public MultiFileBOP getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultiFileBOP multiFile) {
		this.multiFile = multiFile;
	}

	public FileBOP getDownloadSFile_1() {
		return downloadSFile_1;
	}

	public void setDownloadSFile_1(FileBOP downloadSFile_1) {
		this.downloadSFile_1 = downloadSFile_1;
	}

	public FileBOP getDownloadSFile_2() {
		return downloadSFile_2;
	}

	public void setDownloadSFile_2(FileBOP downloadSFile_2) {
		this.downloadSFile_2 = downloadSFile_2;
	}

	public String getDownloadSFile_3() {
		return downloadSFile_3;
	}

	public void setDownloadSFile_3(String downloadSFile_3) {
		this.downloadSFile_3 = downloadSFile_3;
	}

	public MultiFileBOP getDownloadMFile() {
		return downloadMFile;
	}

	public void setDownloadMFile(MultiFileBOP downloadMFile) {
		this.downloadMFile = downloadMFile;
	}

	public String getDownloadSFile_4() {
		return downloadSFile_4;
	}

	public void setDownloadSFile_4(String downloadSFile_4) {
		this.downloadSFile_4 = downloadSFile_4;
	}
}
