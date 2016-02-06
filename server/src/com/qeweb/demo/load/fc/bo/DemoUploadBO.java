package com.qeweb.demo.load.fc.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * demo: 上传和下载示例.
 * 路径: 装载-上传和下载
 */
public class DemoUploadBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4190688190784360008L;

	private FileBOP singleFile;					//单文件
	private FileBOP singleFile2;				//单文件2
	private MultiFileBOP multiFile;				//多文件
	private MultiFileBOP multiFile2;			//多文件2
	private String bop1;
	private String bop2;
	
	public DemoUploadBO() {
		super();
		addBOP("bop1", new StatusBOP());
		getBOP("bop2").setValue("test");
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		List<DemoUploadBO> boList = new LinkedList<DemoUploadBO>();
		
		DemoUploadBO bo1 = new DemoUploadBO();
		bo1.setId(1);
		FileBOP singleFileBop1 = new FileBOP();
		singleFileBop1.setPath("中文a.xlsx");
		singleFileBop1.setValue("中文a.xlsx");
		bo1.addBOP("singleFile", singleFileBop1);
		boList.add(bo1);
		
		DemoUploadBO bo2 = new DemoUploadBO();
		bo2.setId(2);
		FileBOP singleFileBop2 = new FileBOP();
		singleFileBop2.setPath("中文b.xlsx");
		singleFileBop2.setValue("中文b.xlsx");
		bo2.addBOP("singleFile", singleFileBop2);
		boList.add(bo2);
	
		Page page = new Page(boList, boList.size(), getPageSize(), start);
		initPreferencePage(page);

		return page;
	}

	/**
	 * 保存
	 * @param boList
	 */
	public void save(List<DemoUploadBO> boList) {
		System.out.println("----------------------------------------------------------save------------------------------------------------------");
		System.out.println("|\t\t\t\t\t\tboList.size = " + boList.size() + "\t\t\t\t\t\t\t\t\t");
		System.out.println("|\tsingleFile\t\t\t\tsingleFile2\t\t\t\tbop1\t\t\tbop2");
		for (DemoUploadBO bo : boList) {
			String path2 = bo.getSingleFile2() == null ? "" : bo.getSingleFile2().getPath();
			String path1 = bo.getSingleFile() == null ? "" : bo.getSingleFile().getPath();
			System.out.println("|\t" + path1 + "\t\t\t" + path2 + "\t\t\t"
					+ bo.getBop1() + "\t\t\t"
					+ bo.getBop2());
		}
		System.out.println("----------------------------------------------------------end ------------------------------------------------------");
	}
	
	public void save(DemoUploadBO bo) {
		System.out.println("----------------------------------------------------------save------------------------------------------------------");
		System.out.println("|\tsingleFile = " + bo.getSingleFile().getPath());
		String multiFilePath = "";
		String multiFileName = "";
		List<FileBOP> files = getMultiFile().getFiles();
		if(ContainerUtil.isNotNull(files)) {
			for (FileBOP fileBOP : files) {
				multiFilePath += fileBOP.getPath() + ",";
				multiFileName += fileBOP.getDisplayName() + ",";
			}
		}
		System.out.println("|\tmultiFile = " + StringUtils.removeEnd(multiFilePath) + "\tmultiFileNames = " + multiFileName);
		System.out.println("----------------------------------------------------------end ------------------------------------------------------");
	}
	
	public FileBOP getSingleFile() {
		return singleFile;
	}

	public void setSingleFile(FileBOP singleFile) {
		this.singleFile = singleFile;
	}

	public MultiFileBOP getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultiFileBOP multiFile) {
		this.multiFile = multiFile;
	}

	public String getBop1() {
		return bop1;
	}

	public void setBop1(String bop1) {
		this.bop1 = bop1;
	}

	public String getBop2() {
		return bop2;
	}

	public void setBop2(String bop2) {
		this.bop2 = bop2;
	}

	public FileBOP getSingleFile2() {
		return singleFile2;
	}

	public void setSingleFile2(FileBOP singleFile2) {
		this.singleFile2 = singleFile2;
	}

	public MultiFileBOP getMultiFile2() {
		return multiFile2;
	}

	public void setMultiFile2(MultiFileBOP multiFile2) {
		this.multiFile2 = multiFile2;
	}
}
