package com.qeweb.framework.bc.prop;

import java.util.Set;

import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;


/**
 * <ul>多文件上传范围,用于限制上传文件的总数/总容量/文件类型.</ul>
 * <ul>默认值可全局配置, 
 * 	<li>包括maxFileNum: 最大可上传文件数, 值为“-1”时不限制上传数量;
 * 	<li>包括maxFileNum: 最大可上传文件总容量, 单位：字节, 值为“-1”时不限制上传总容量;
 * </ul>
 * <ul>MultiFileRange 仅运用于MultiFileBop<br>
 */
public class MultiFileRange extends Range {
	private static final long serialVersionUID = 6031901355957284323L;
	private Integer maxFileNum;			//最大可上传文件数，值小于0时不限制上传数量
	private Long maxFileSize;			//最大可上传文件总容量,单位：字节，值小于0时不限制上传总容量
	private Integer uploadNum;			//当前上传文件数
	private Long uploadSize;			//当前上传文件总容量,单位：字节
	private FileBOP fileBop;			//当前上传文件BOP
	private Set<String> allowedType;	//允许上传类型, 元素为文件扩展名, 如xml, doc, txt
	private Set<String> notAllowedType;	//不允许上传类型, 元素为文件扩展名,如xml, doc, txt
		
//	public MultiFileRange(){
//		this.maxFileNum = AppConfig.getMultifileMaxNum();
//		this.maxFileSize = AppConfig.getMultifileMaxSize();
//	}
//	
//	public MultiFileRange(Integer maxFileNum, Long maxFileSize){
//		this.maxFileNum = (maxFileNum == null ? AppConfig.getMultifileMaxNum() : maxFileNum);
//		this.maxFileSize = (maxFileSize == null ? AppConfig.getMultifileMaxSize() : maxFileSize);
//	}
	
	@Override
	public boolean isIN(Value value) {
		StringBuffer errorMsg = new StringBuffer();
		//允许上传数已满
		if(getMaxFileNum() < getUploadNum()){
			errorMsg.append(AppLocalization.getLocalization("multifile.upload.maxNum.part1"));
			errorMsg.append(getMaxFileNum());
			errorMsg.append(AppLocalization.getLocalization("multifile.upload.maxNum.part2"));
			this.setValidateMessage(errorMsg.toString());
			return false;
		}
		//文件过大导致整体超出允许范围			
		if(checkMultiFileSzie()){
			errorMsg.append(AppLocalization.getLocalization("multifile.upload.maxSize.part1"));
			errorMsg.append((getMaxFileSize() / 1024 / 1024) + "M");			
			errorMsg.append(AppLocalization.getLocalization("multifile.upload.maxSize.part2"));
			this.setValidateMessage(errorMsg.toString());
			return false;
		}
		FileRange range = getFileBop().getFileRange();
		range.setAllowedType(getAllowedType());
		range.setNotAllowedType(getNotAllowedType());
		if(!range.isIN(value)){
			this.setValidateMessage(range.getValidateMessage());
			return false;
		}
		
		return true;
	}

	/**
	 * 是否超过可承受上传存储总量
	 * @param uploadSize
	 * @param maxSize
	 * @return
	 */
	final protected boolean checkMultiFileSzie() {
		//不受限制
		if(this.getMaxFileSize() < 0)
			return false;
		else if(this.getMaxFileSize() < this.getUploadSize())
			return true;
		return false;
	}

	/**
	 * 最大可上传文件数，值小于0时不限制上传数量
	 * @return
	 */
	public Integer getMaxFileNum() {
		if(maxFileNum == null)
			maxFileNum = AppConfig.getMultifileMaxNum();
		return maxFileNum;
	}

	/**
	 * 设置最大可上传文件数，值小于0时不限制上传数量
	 * @param maxFileNum
	 */
	public void setMaxFileNum(Integer maxFileNum) {
		this.maxFileNum = maxFileNum;
	}

	/**
	 * 最大可上传文件总容量,单位：字节，值小于0时不限制上传总容量
	 * @return
	 */
	public Long getMaxFileSize() {
		if(maxFileSize == null)
			maxFileSize = AppConfig.getMultifileMaxSize();
		return maxFileSize;
	}

	/**
	 * 设置最大可上传文件总容量,单位：字节，值小于0时不限制上传总容量
	 * @param maxFileSize
	 */
	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public Integer getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(Integer uploadNum) {
		this.uploadNum = uploadNum;
	}

	public Long getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(Long uploadSize) {
		this.uploadSize = uploadSize;
	}

	public FileBOP getFileBop() {
		return fileBop;
	}

	public void setFileBop(FileBOP fileBop) {
		this.fileBop = fileBop;
	}

	/**
	 * 允许上传类型, 元素为文件扩展名, 如xml, doc, txt
	 * @return
	 */
	public Set<String> getAllowedType() {
		if(ContainerUtil.isNull(allowedType))
			allowedType = ContainerUtil.convertToSet(AppConfig.getAllowedType(), ",");
		return allowedType;
	}

	/**
	 * 设置允许上传类型, 元素为文件扩展名, 如xml, doc, txt
	 * @param allowedType
	 */
	public void setAllowedType(Set<String> allowedType) {
		this.allowedType = allowedType;
	}

	/**
	 * 不允许上传类型, 元素为文件扩展名,如xml, doc, txt
	 * @return
	 */
	public Set<String> getNotAllowedType() {
		if(ContainerUtil.isNull(notAllowedType))
			notAllowedType = ContainerUtil.convertToSet(AppConfig.getNotAllowedType(), ",");
		return notAllowedType;
	}

	/**
	 * 设置不允许上传类型, 元素为文件扩展名,如xml, doc, txt
	 * @param notAllowedType
	 */
	public void setNotAllowedType(Set<String> notAllowedType) {
		this.notAllowedType = notAllowedType;
	}
}
