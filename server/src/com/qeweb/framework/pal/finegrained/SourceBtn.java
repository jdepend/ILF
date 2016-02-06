package com.qeweb.framework.pal.finegrained;


import com.qeweb.framework.app.pageflow.PageFlowPool;
import com.qeweb.framework.app.pageflow.SysPageflow;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pl.DialogHelper;

/**
 * 细粒度组件右侧的选择按钮,通过该按钮可以弹出选择框.
 * <br>
 * SourceBtn依附于细粒度组件,不能单独存在
 */
public abstract class SourceBtn extends ViewComponent {
	final private String SM_RADIO = "radio";
	final private String SM_CHECKBOX = "checkbox";
	private String sm = SM_RADIO;	//弹出框的selectionModel,可选值:radio/checkbox,默认为radio

	private String bindBop;			//绑定的bo对象名称,其格式为:sbop1:tbop1,sbop2:tbop2,表示弹出框中的sbop1/sbop2将分别赋值到tbop1/tbop2
	private String editAble;		//可编辑的 bop, 其格式为:tbop1,tbop2,tbop3, 表示tbop1,tbop2,tbop3三个组件的状态为可编辑(默认为只读) 
	private String sbopIds;			//待填充的bopId, 以 "," 分隔
	private String tbopIds;			//目标页面对应的bopId, 以 "," 分隔 

	/*
	 * 查找弹出页面的参数, 如果pageParam=false, 则页面流中仅需要根据bindBo,bindBop查找;
	 * 如果pageParam=true, 则页面流中需要根据bindBo,bindBop,sourcePage共同查找;
	 */
	private boolean pageParam;
	private String sourcePage;		//sourceBtn所在的jsp页面, 当pageParam==true时有效
	
	private String sysOperate = "";	//sourceBtn所在的系统弹出框类型(新增弹出框/修改弹出框)
	
	/*
     * 弹出页面执行的方法, 格式bo1.method1,bo2.method2.
	 * 当弹出页面的组件接收参数时, 将会执行operate指定的方法; 
	 * 如果operate为空且弹出页面的组件接收参数, 则执行组件绑定BO的query方法
     */
	private String operate;
	
	private SysPageflow pageflow;	//sourceBtn对应的页面流信息

	/**
	 * 取得弹出页面的jsp
	 * @return
	 */
	public String getDialogPath() {
		return getPageflow() != null ? getPageflow().getTargetPage() : "";
	}
	
	/**
	 * 弹出页面是否有关闭按钮
	 * @return
	 */
	public boolean hasCloseBtn() {
		return getPageflow() != null ? getPageflow().isHasCloseBtn() : AppConfig.hasCloseBtn();
	}

	/**
	 * 取得弹出页面的宽度
	 * @return
	 */
	public String getDialogWidth() {
		return getPageflow() != null ? pageflow.getDialogWidth() : "";
	}

	/**
	 * 取得弹出页面的高度
	 * @return
	 */
	public String getDialogHeight() {
		return getPageflow() != null ? pageflow.getDialogHeight() : "";
	}
	
	/**
	 * 取得弹出页面的标题
	 * @return
	 */
	public String getDialogTitle() {
		if(StringUtils.isNotEmpty(getText()))
			return getText();
		return DialogHelper.getHelper().getDialogTitle(getPageflow());
	}
	
	/**
	 * 取得弹出页面的图标
	 * @return
	 */
	public String getDialogIcon() {
		return getPageflow() != null ? pageflow.getDialogIcon() : "";
	}
	
	/**
	 * 取得重复者判断列
	 */
	public String getEcho() {
		return getPageflow() != null ? getPageflow().getEcho() : "";
	}

	private SysPageflow getPageflow() {
		if(pageflow != null)
			return pageflow;
		
		String spage = null;
		if(pageParam)
			spage = sourcePage;

		pageflow = (SysPageflow) PageFlowPool.getDialog(getBcId(), bindBop, spage);
		return pageflow;
	}

	public String getSbopIds() {
		return sbopIds;
	}

	public void setSbopIds(String sbopIds) {
		this.sbopIds = sbopIds;
	}

	public String getTbopIds() {
		return tbopIds;
	}

	public void setTbopIds(String tbopIds) {
		this.tbopIds = tbopIds;
	}

	public String getBindBop() {
		return bindBop;
	}
	public void setBindBop(String bindBop) {
		this.bindBop = bindBop;
	}
	public String getSm() {
		if(StringUtils.isEmpty(sm))
			return SM_RADIO;
		else if(StringUtils.isEqualIgnoreCase(sm, SM_RADIO))
			return SM_RADIO;
		else if(StringUtils.isEqualIgnoreCase(sm, SM_CHECKBOX))
			return SM_CHECKBOX;
		else
			return SM_RADIO;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public boolean isPageParam() {
		return pageParam;
	}
	public void setPageParam(boolean pageParam) {
		this.pageParam = pageParam;
	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	@Override
	public void free() {
		sm = SM_RADIO;
		bindBop = null;
		sbopIds = null;
		tbopIds = null;
		pageParam = false;
		sourcePage = null;
		operate = null;
		editAble = null;

		super.free();
	}

	public String getSysOperate() {
		return sysOperate;
	}

	public void setSysOperate(String sysOperate) {
		this.sysOperate = sysOperate;
	}

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

	public String getEditAble() {
		return editAble;
	}

	public void setEditAble(String editAble) {
		this.editAble = editAble;
	}
}
