package com.qeweb.framework.pal.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.QewebException;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

abstract public class FileField extends FinegrainedComponent {

	private String operate;				//自定义上传方法
	private String operateText;			//上传按钮的自定义名称
	private FileBOP fileBop;			//单文件上传BOP
	private MultiFileBOP multiFileBOP;	//多文件上传BOP
	private boolean download = true;	//显示下载链接，默认为显示
	private boolean showRange = false;	//显示上传限制，默认为不显示

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getOperateText() {
		return StringUtils.isEmpty(operateText) ? AppLocalization.getLocalization("upload") : operateText;
	}

	public void setOperateText(String operateText) {
		this.operateText = operateText;
	}


	/**
	 * 是否多文件上传
	 * @return
	 */
	private boolean isMulti(){
		if(BoOperateUtil.getRealBop(super.getBc()) instanceof MultiFileBOP)
			return true;
		return false;
	}

	/**
	 * 获取单文件上传BOP
	 * @return
	 */
	public FileBOP getFileBOP() {
		if(fileBop != null)
			return fileBop;

		BOProperty bop = BoOperateUtil.getRealBop(super.getBc());
		if(bop instanceof FileBOP)
			fileBop = (FileBOP) bop;

		return fileBop;
	}

	/**
	 * 获取多文件上传BOP
	 * @return
	 */
	public MultiFileBOP getMultiFileBOP() {
		if(multiFileBOP != null)
			return multiFileBOP;
		BOProperty bop = BoOperateUtil.getRealBop(super.getBc());
		if(bop instanceof MultiFileBOP)
			multiFileBOP = (MultiFileBOP) bop;

		return multiFileBOP;
	}

	public void paint() {
		if(isErrorBop()){
			QewebException e = new QewebException("", "fileField标签未绑定正确的bop，请绑定FileBOP（单文件）或MultiFileBOP（多文件）");
			e.printStackTrace();
			return;
		}
		//多文件上传
		if(isMulti()){
			paintMultiple();
		}
		//单文件上传
		else{
			paintSingle();
		}
	}

	/**
	 * 判断绑定的bop是否符合标签规范
	 * @return
	 */
	private boolean isErrorBop() {
		BOProperty bop = BoOperateUtil.getRealBop(super.getBc());
		return !(bop instanceof FileBOP || bop instanceof MultiFileBOP);
	}

	@Override
	public void free() {
		operate = null;
		operateText = null;
		fileBop = null;
		multiFileBOP = null;
		download = true;
		showRange = false;

		super.free();
	}

	/**
	 * 单文件上传
	 */
	abstract public void paintSingle();

	/**
	 * 多文件上传
	 */
	abstract public void paintMultiple();

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public boolean isShowRange() {
		return showRange;
	}

	public void setShowRange(boolean showRange) {
		this.showRange = showRange;
	}
}