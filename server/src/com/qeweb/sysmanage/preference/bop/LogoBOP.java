package com.qeweb.sysmanage.preference.bop;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.bc.prop.FileRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.sysmanage.preference.bop.range.LogoRange;

/**
 * 上传logo图片
 */
public class LogoBOP extends FileBOP {

	private static final long serialVersionUID = -8703796319424513999L;
	
	@Override
	public boolean upload(){
		try {
			//保存目录名
			String uploadDirName = getCustomDir();
			//保存文件名
			String saveFileName = FileHandleHelp.getFileSaveName(getDisplayName());
			File savefile = new File(FileHandleHelp.makdSaveDir(uploadDirName), saveFileName);		
			FileUtils.copyFile(getFile(), savefile);
			this.setPath(FileHandleHelp.getFileURLPath(saveFileName, uploadDirName));
			setDisplayName("LOGO");
			return true;
		} catch (IOException e) {	
			return false;
		}
	}
	
	@Override
	public FileRange getFileRange() {
		for(Range range: getRange().getRangeList()){
			if(range instanceof LogoRange)
				return (FileRange) range;
		}
		LogoRange range = new LogoRange();
		getRange().addRange(range, LogicEnum.AND);
		return range;
	}
	
	@Override
	protected String getCustomDir() {
		return "images/logo";
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(sourceBop instanceof LogoTypeBOP) {
			LogoTypeBOP bop = (LogoTypeBOP) sourceBop;
			if(bop.isCustomType()) {
				getStatus().setHidden(false);
			}
			else {
				clear();
				getStatus().setHidden(true);
			}
		}

		return this;
	}
}
