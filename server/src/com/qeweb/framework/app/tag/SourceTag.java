package com.qeweb.framework.app.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.qeweb.framework.app.tag.control.CommandButtonTag;
import com.qeweb.framework.app.tag.finegrained.FineGrainedTag;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.manager.VCManager;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.SourceBtn;

/**
 * 细粒度组件的子标签,用于展示弹出框按钮,并负责定义弹出框的返回值.
 * <br>
 * 用法:<br>
 * &lt;qeweb:hidden bind='id'/>
 * &lt;qeweb:textField bind='userName'>
 * 		<qeweb:source bind='uesrBo' bind='tId:id,tUserName:userName'/>
 * &lt;/qeweb:textField>
 * <br>
 * tId和tUserName是userB的属性,弹出框中的tId和tUserName将返回给id和userName.
 * <br>
 * 
 * 
 */
public class SourceTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String bindBo;			//绑定的bop对象名称
	private String bindBop;			//绑定的bop对象名称,其格式为:tbop1:sbop1,tbop2:sbop2,表示弹出框中的sbop1/sbop2将分别赋值到tbop1/tbop2
	private String editAble;		//可编辑的 bop, 其格式为:tbop1,tbop2,tbop3, 表示tbop1,tbop2,tbop3三个组件的状态为可编辑(默认为只读) 
	private String text; 			//弹出框的国际化资源标识
	private String sm;				//弹出框的selectionModel,可选值:radio/checkbox,默认为radio
	/*
	 * 查找弹出页面的参数, 如果pageParam=false, 则页面流中仅需要根据bindBo,bindBop查找;
	 * 如果pageParam=rue, 则页面流中需要根据bindBo,bindBop,sourcePage共同查找;
	 */
	private String pageParam;
	/*
     * 弹出页面执行的方法, 格式bo1.method1,bo2.method2.
	 * 当弹出页面的组件接收参数时, 将会执行operate指定的方法; 
	 * 如果operate为空且弹出页面的组件接收参数, 则执行组件绑定BO的query方法
     */
    private String operate;
	
	final public int doStartTag() throws JspException {
		//sourceBtn没有使用实例池
		SourceBtn sourceBtn = (SourceBtn) VCManager.getVCFactory().createVC(SourceBtn.class);
		sourceBtn.setBcId(bindBo);
		sourceBtn.setBindBop(getBindBop());
		sourceBtn.setText(text);
		sourceBtn.setSm(sm);
		sourceBtn.setPageParam(Boolean.valueOf(pageParam));
		sourceBtn.setOperate(operate);
		sourceBtn.setSourcePage(Envir.getRequestURI());
		
		ViewComponent parentVC = null;
		if(getParent() instanceof FineGrainedTag) {
			FinegrainedComponent fc = ((FineGrainedTag)getParent()).getFc();
			fc.setSourceBtn(sourceBtn);
			parentVC = fc;
		}
		else {
			CommandButton btn = ((CommandButtonTag)getParent()).getButton();
			btn.setSourceBtn(sourceBtn);
			parentVC = btn;
		}
		
		setBopIds(sourceBtn, parentVC.getParent().getName());
		sourceBtn.setParent(parentVC);
		sourceBtn.setPageContextInfo(parentVC.getPageContextInfo());
		
		return SKIP_BODY;
	}

	private void setBopIds(SourceBtn sourceBtn, String containerName) {
		String[] bopIds = StringUtils.split(getBindBop(), ConstantSplit.SEMICOLON_SPLIT);
		String sbopIds = "";
		String tbopIds = "";
		
		for(String ids : bopIds) {
			String[] temp = StringUtils.split(ids, ConstantSplit.COLON_SPLIT);
			sbopIds += VCUtil.createFinegrainedID(containerName, temp[0]) + ConstantSplit.COMMA_SPLIT;
			tbopIds += VCUtil.createFinegrainedID(containerName, temp[1]) + ConstantSplit.COMMA_SPLIT;
		}

		String[] editAbleArr = StringUtils.split(getEditAble(), ConstantSplit.COMMA_SPLIT);
		if(StringUtils.isNotEmpty(editAbleArr)) {
			String editAble = "";
			for(String bind : editAbleArr) {
				editAble += VCUtil.createFinegrainedID(containerName, bind) + ConstantSplit.COMMA_SPLIT;
			}
			sourceBtn.setEditAble(StringUtils.removeEnd(editAble));
		}
		
		
		sourceBtn.setSbopIds(StringUtils.removeEnd(sbopIds));
		sourceBtn.setTbopIds(StringUtils.removeEnd(tbopIds));
	}
	
	public String getBindBo() {
		return bindBo;
	}

	public void setBindBo(String bindBo) {
		this.bindBo = bindBo;
	}

	public String getBindBop() {
		return StringUtils.removeAllSpace(bindBop);
	}

	public void setBindBop(String bindBop) {
		this.bindBop = bindBop;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}


	public String getPageParam() {
		return pageParam;
	}


	public void setPageParam(String pageParam) {
		this.pageParam = pageParam;
	}

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

	public String getEditAble() {
		return StringUtils.removeAllSpace(editAble);
	}

	public void setEditAble(String editAble) {
		this.editAble = editAble;
	}
}
