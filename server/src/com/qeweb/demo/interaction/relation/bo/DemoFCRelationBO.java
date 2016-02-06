package com.qeweb.demo.interaction.relation.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.common.bo.DemoPurchaseOrderBO;
import com.qeweb.demo.interaction.relation.bop.DemoAattachmentFlagBOP;
import com.qeweb.demo.interaction.relation.bop.DemoAreaFormulaBOP;
import com.qeweb.demo.interaction.relation.bop.DemoAttachmentBOP;
import com.qeweb.demo.interaction.relation.bop.DemoCheckLogoBOP;
import com.qeweb.demo.interaction.relation.bop.DemoEmployeeNameBOP;
import com.qeweb.demo.interaction.relation.bop.DemoEmployeeSexBOP;
import com.qeweb.demo.interaction.relation.bop.DemoIncomeBOP;
import com.qeweb.demo.interaction.relation.bop.DemoIncomeDescBOP;
import com.qeweb.demo.interaction.relation.bop.DemoLogoBOP;
import com.qeweb.demo.interaction.relation.bop.DemoMultiAttachmentBOP;
import com.qeweb.demo.interaction.relation.bop.DemoOtherSoftwareBOP;
import com.qeweb.demo.interaction.relation.bop.DemoPercentBOP;
import com.qeweb.demo.interaction.relation.bop.DemoSoftwareBOP;
import com.qeweb.demo.interaction.relation.prop.PercentRange;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.AreaBOP;

/**
 * demo: 细粒度组件关联示例1.
 * 路径: 交互-关联
 */
public class DemoFCRelationBO extends DemoPurchaseOrderBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7535633690167187256L;

	private String area; 				// 区
	
	private String demoCheckLogo;		//logo选择
	private String demoLogo;			//logo
	
	private String employeeSex;			//员工性别
	private String employeeName;		//员工姓名
	
	private String demoIncome;			//收入
	private String demoIncomeDesc;		//其他描述
	
	private Integer length;				//长
	private Integer width;				//宽
	private Integer acreage;			//面积
	
	private double percent;				//百分比
	private double money;				//金额
	private double total;				//合计
	
	private String software;			//采用哪些软件系统
	private String otherSoftware;		//其它系统软件名称
	private String allSoftware;			//所有软件
	
	private String attachmentFlag;		//是否上传附件
	private FileBOP attachment;
	private MultiFileBOP multiAttachment;
	
	public DemoFCRelationBO() {
		super();
		addBOP("area", new AreaBOP());
		addBOP("employeeSex", new DemoEmployeeSexBOP());
		addBOP("employeeName", new DemoEmployeeNameBOP());
		addBOP("demoCheckLogo", new DemoCheckLogoBOP());
		addBOP("demoLogo", new DemoLogoBOP());
		addBOP("demoIncome", new DemoIncomeBOP());
		addBOP("demoIncomeDesc", new DemoIncomeDescBOP());
		addBOP("software", new DemoSoftwareBOP());
		addBOP("otherSoftware", new DemoOtherSoftwareBOP());
		addBOP("allSoftware", new DemoSoftwareBOP());
		
		addBOP("length", new DemoAreaFormulaBOP());
		addBOP("width", new DemoAreaFormulaBOP());
		addBOP("percent", new DemoPercentBOP());
		getBOP("percent").addRange(new PercentRange());
		getBOP("percent").setValue("0.1");
		addBOP("money", new DemoPercentBOP());
		
		getBOP("employeeName").setValue("1,5");
		getBOP("allSoftware").getStatus().setReadonly(true);
		getBOP("allSoftware").setValue(DemoSoftwareBOP.SOFTWARE_TYPE.MRP + "," + DemoSoftwareBOP.SOFTWARE_TYPE.SAP);
		
		addBOP("attachmentFlag", new DemoAattachmentFlagBOP());
		addBOP("attachment", new DemoAttachmentBOP());
		addBOP("multiAttachment", new DemoMultiAttachmentBOP());
	}
	
	/**
	 * 处理长/宽/面积的关联
	 */
	@Override
	public Object bopRelationHandle(BusinessObject relationBo) throws BOException {
		DemoFCRelationBO bo = (DemoFCRelationBO)relationBo;
		if(bo.getLength() != null && bo.getWidth() != null){
			bo.setAcreage(bo.getLength() * bo.getWidth());
		}
		
		bo.setTotal(bo.getMoney() * bo.getPercent());
		bo.getBOP("acreage").getStatus().setReadonly(true);
		
		return bo;
	}
	
	@SuppressWarnings("unchecked")
	protected void initPreferencePage(Page page) {
		super.initPreferencePage(page);
		
		List<DemoFCRelationBO> result = new LinkedList<DemoFCRelationBO>();
		List<DemoPurchaseOrderBO> boList = (List<DemoPurchaseOrderBO>) page.getBOList();
		//注意: 由于返回的是List<DemoPurchaseOrderBO>, 此处需要将DemoPurchaseOrderBO转换为DemoFCValidationBO
		try {
			int i = 0;
			for (DemoPurchaseOrderBO demoPurchaseOrderBO : boList) {
				DemoFCRelationBO demoFCRelationBO = new DemoFCRelationBO();
				demoFCRelationBO.setWidth(100);
				demoFCRelationBO.setLength(20);
				demoFCRelationBO.setAcreage(100 * 20);
				PropertyUtils.copyProperties(demoFCRelationBO, demoPurchaseOrderBO);
				
				if((i & 1) == 0) {
					OperateBOP opt = new OperateBOP();
					opt.getStatus().setHidden(true);
					demoFCRelationBO.addOperateBOP("submit", opt);
				}
				i++;
				
				BOHelper.initPreferencePage_lazy(demoFCRelationBO, this);
				result.add(demoFCRelationBO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		page.setBOList(result);
	}
	
	/**
	 * 
	 * @param bo
	 */
	public void save(DemoFCRelationBO bo) {
		System.out.println("----------------------------------------------------------save------------------------------------------------------");
		System.out.println("|\tprovince = " + bo.getProvince() + "\tcity = " + bo.getCity() + "\tarea = " + bo.getArea());
		System.out.println("|\temployeeSex = " + bo.getEmployeeSex() + "\temployeeName = " + bo.getEmployeeName());
		System.out.println("|\t收入类型 = " + bo.getDemoIncome() + "\t其它收入 = " + bo.getDemoIncomeDesc());
		System.out.println("|\tlogoType = " + bo.getDemoCheckLogo() + "\tlogo路径 = " + bo.getDemoLogo());
		System.out.println("|\t软件类型 = " + bo.getSoftware() + "\t其它软件 = " + bo.getOtherSoftware());
		System.out.println("|\t软件展示 = " + bo.getAllSoftware());
		System.out.println("|\t附件= " + bo.getAttachment().getPath() + "\t" + bo.getAttachment().getValue().getValue());
		
		String multiFilePath = "";
		String multiFileName = "";
		List<FileBOP> files = bo.getMultiAttachment().getFiles();
		if(ContainerUtil.isNotNull(files)) {
			for (FileBOP fileBOP : files) {
				multiFilePath += fileBOP.getPath() + ",";
				multiFileName += fileBOP.getDisplayName() + ",";
			}
		}
		System.out.println("|\t多附件= " + StringUtils.removeEnd(multiFilePath) + "\t = " + multiFileName);
		System.out.println("----------------------------------------------------------------end-------------------------------------------------");
	}
	
	/**
	 * 
	 * @param boList
	 */
	public void save(List<DemoFCRelationBO> boList) {
		System.out.println("----------------------------------------------------------save------------------------------------------------------");
		if(ContainerUtil.isNotNull(boList)) {
			for (DemoFCRelationBO bo : boList) {
				System.out.println("|\tlength = " + bo.getLength() + "\twidth = " + bo.getWidth() + "\tacreage = " + bo.getAcreage());
			}
		}
		System.out.println("----------------------------------------------------------------end-------------------------------------------------");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getSearchClass() {
		return DemoPurchaseOrderBO.class;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDemoCheckLogo() {
		return demoCheckLogo;
	}

	public void setDemoCheckLogo(String demoCheckLogo) {
		this.demoCheckLogo = demoCheckLogo;
	}

	public String getDemoLogo() {
		return demoLogo;
	}

	public void setDemoLogo(String demoLogo) {
		this.demoLogo = demoLogo;
	}

	public String getEmployeeSex() {
		return employeeSex;
	}

	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDemoIncome() {
		return demoIncome;
	}

	public void setDemoIncome(String demoIncome) {
		this.demoIncome = demoIncome;
	}

	public String getDemoIncomeDesc() {
		return demoIncomeDesc;
	}

	public void setDemoIncomeDesc(String demoIncomeDesc) {
		this.demoIncomeDesc = demoIncomeDesc;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getAcreage() {
		return acreage;
	}

	public void setAcreage(Integer acreage) {
		this.acreage = acreage;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getOtherSoftware() {
		return otherSoftware;
	}

	public void setOtherSoftware(String otherSoftware) {
		this.otherSoftware = otherSoftware;
	}

	public String getAllSoftware() {
		return allSoftware;
	}

	public void setAllSoftware(String allSoftware) {
		this.allSoftware = allSoftware;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getAttachmentFlag() {
		return attachmentFlag;
	}

	public void setAttachmentFlag(String attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}

	public FileBOP getAttachment() {
		return attachment;
	}

	public void setAttachment(FileBOP attachment) {
		this.attachment = attachment;
	}

	public MultiFileBOP getMultiAttachment() {
		return multiAttachment;
	}

	public void setMultiAttachment(MultiFileBOP multiAttachment) {
		this.multiAttachment = multiAttachment;
	}
}
