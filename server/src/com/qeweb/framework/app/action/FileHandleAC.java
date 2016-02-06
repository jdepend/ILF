package com.qeweb.framework.app.action;

import java.io.File;
import java.io.IOException;

import com.qeweb.framework.app.handler.IFileHandler;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.FileRange;
import com.qeweb.framework.bc.prop.MultiFileRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.FileBopUtil;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.manager.BOManager;

/**
 * 处理文件上传
 * 
 * 
 */
public class FileHandleAC extends BaseAction implements IFileHandler {

	private static final long serialVersionUID = 13423432432L;

	private File attachment; 				// 附件
	private String attachmentFileName; 		// 附件名称FileName固定写法
	private String attachmentContentType; 	// 类型ContentType固定写法
	
	private String sourceName;				//BO bind
	private String bopName;					//BOP name
	private String operate;					//BOP method
	private String lastPath;				//上次上传文件的路径
	private String uploadSize;				//已上传文件总大小，由于进行文件删除时需要对其进行缩减，因此该字段的表现形式为将每个上传文件的大小以英文逗号（,）形式分割，例如"1024,2048,"
	private Integer uploadNum; 				//已上传文件数量
	
	private Boolean isMulti = false;		//是否多文件上传

	@Override
	public String upload() throws IOException {
		getResponse().setContentType(Constant.CONTENTTYPE);

		FileBOP bop = null;
		MultiFileBOP mbop = null;

		BusinessObject bo = BOManager.createBO(sourceName);
		//获取上传文件的BOP
		BOProperty boproperty = bo.getBOP(bopName);
		if(getIsMulti()){
			mbop = FileBopUtil.getMultiFileBOP(boproperty);
			bop = mbop.getItemBopObject();
		}
		else{
			bop = FileBopUtil.getFileBop(boproperty);
		}
		
		if(bop == null){
			writerJson(false, AppLocalization.getLocalization("file.upload.exception"), null, null);
		}
		else if(checkUploadFile(bop, mbop)) {
			//向bop中赋值
			bop.setDisplayName(attachmentFileName);
			bop.setFile(attachment);
			bop.setLastPath(lastPath);
			
			boolean result = false;
			if(StringUtils.isNotEmptyStr(operate)){
				//执行自定义方法
				result = FileBopUtil.executeBOPMethod(bop, operate);
			}
			else {
				//执行默认方法
				result = bop.upload();
			}
			
			//设置结果
			bop.setResult(result);
			
			if(bop.getSuccess()){
				this.writerJson(true, bop.getMessage(), bop.getDisplayName(), bop.toLink());
			}
			else {
				this.writerJson(false, bop.getMessage(), null, null);
			}
		}

		return null;
	}

	/**
	 * 检查上传文件是否合法
	 * @param bop
	 * @return
	 * @throws IOException
	 */
	private boolean checkUploadFile(FileBOP bop, MultiFileBOP mbop) throws IOException {
		Range range = bop.getFileRange();
		((FileRange) range).setFile(attachment);
		((FileRange) range).setDisplayName(attachmentFileName);
		
		//多文件上传
		if(mbop != null){
			range = mbop.getMultiFileRange();
			((MultiFileRange) range).setFileBop(bop);
			((MultiFileRange) range).setUploadNum(++uploadNum);
			uploadSize += ConstantSplit.COMMA_SPLIT + attachment.length() ;
			((MultiFileRange) range).setUploadSize(FileHandleHelp.getUploadSize(uploadSize, attachment.length()));
		}
		
		if(range.isIN(null)){
			return true;
		}
		else {
			this.writerJson(false, range.getValidateMessage(), null, null);
			return false;
		}
	}

	/**
	 * 回写结果JSON
	 * @param success
	 * @param message
	 * @param fileName
	 * @param filePath
	 * @throws IOException
	 */
	private void writerJson(boolean success, String message, String fileName, String filePath) throws IOException {
		StringBuffer sbr = new StringBuffer();
		sbr.append("{success:");
		sbr.append(success);
		sbr.append(",message:'");
		if(success){
			sbr.append(AppLocalization.getLocalization("file.upload.message.upload.success"));
			if(StringUtils.isNotEmpty(message))
				sbr.append(message);
			sbr.append("',fileName:'").append(fileName).append("'");
			if(StringUtils.isNotEmpty(filePath)){
				sbr.append(",filePath:'").append(filePath).append("'");
			}
			if(getIsMulti()){
				sbr.append(",uploadNum:").append(getUploadNum());
				sbr.append(",uploadSize:'").append(getUploadSize()).append("'");
			}
		}
		else{
			sbr.append(AppLocalization.getLocalization("file.upload.message.upload.error"));
			if(StringUtils.isNotEmpty(message))
				sbr.append(message);
			sbr.append("'");
		}
		sbr.append("}");
		getResponse().getWriter().write(sbr.toString());
	}

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getBopName() {
		return bopName;
	}

	public void setBopName(String bopName) {
		this.bopName = bopName;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getLastPath() {
		return lastPath;
	}

	public void setLastPath(String lastPath) {
		this.lastPath = lastPath;
	}

	public String getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(String uploadSize) {
		this.uploadSize = uploadSize;
	}

	public Integer getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(Integer uploadNum) {
		this.uploadNum = uploadNum;
	}

	public Boolean getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(Boolean isMulti) {
		this.isMulti = isMulti;
	}
}
