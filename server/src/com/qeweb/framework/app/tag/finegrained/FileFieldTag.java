package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.other.FileField;


/**
 * 细粒度组件--FILE文件选择标签
 *
 */
public class FileFieldTag extends FineGrainedTag implements Serializable {
	private static final long serialVersionUID = 211L;
	
	private String operate;				//文件控件绑定的行为, 它指定FileBOP中对应的方法
	private String operateText;			//上传按钮的自定义名称
	private String download;			//显示下载链接，默认为显示
	private String showRange;			//显示上传限制，默认为不显示
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		return (FileField) AppManager.createVC(FileField.class);
	}
	
	@Override
	protected void initFC(FinegrainedComponent fc, Container container) {
		FileField fileFC = (FileField) fc;
		fileFC.setName(VCUtil.createFinegrainedName(container.getName(), getBind()));
		fileFC.setId(VCUtil.createFinegrainedID(container.getName(), getBind()));
		fileFC.setBcId(getBind());
		fileFC.setText(getText());
		fileFC.setGroupName(getGroupName());
		fileFC.setParent(container);
		fileFC.setPageContextInfo(container.getPageContextInfo());
		fileFC.setOperate(getOperate());
		fileFC.setOperateText(getOperateText());
		fileFC.setAlign(getAlign());
		if(StringUtils.isNotEmpty(getWidth())){
			fc.setWidth(StringUtils.convertToFloat(getWidth()));
		}
		fileFC.setDownload(Boolean.valueOf(getDownload()));
		fileFC.setShowRange(Boolean.valueOf(getShowRange()));
	}
	
	public String getOperate() {
		return operate;
	}
	
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	public String getOperateText() {
		return operateText;
	}
	
	public void setOperateText(String operateText) {
		this.operateText = operateText;
	}
	public String getDownload() {
		return StringUtils.isEmpty(download) ? "true" : download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getShowRange() {
		return showRange;
	}
	public void setShowRange(String showRange) {
		this.showRange = showRange;
	}

}
