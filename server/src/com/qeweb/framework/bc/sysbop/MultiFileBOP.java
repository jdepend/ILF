package com.qeweb.framework.bc.sysbop;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.MultiFileRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.constant.ConstantSplit;

public class MultiFileBOP extends BOProperty {
	private static final long serialVersionUID = -5986855866029562680L;

	private List<FileBOP> files = new ArrayList<FileBOP>();
			
	public void init() {
		MultiFileRange range = new MultiFileRange();
		getRange().addRange(range);
		this.setRange(getRange());
	}
	
	/**
	 * 获取单个上传文件适用的FileBOP对象<br>
	 * 如需使用自定义FileBOP，请重写该方法
	 * @return
	 */
	public FileBOP getItemBopObject(){
		return new FileBOP();
	}
	
	/**
	 * 获取上传文件限制。<br>
	 * <ul>限制范围：
	 * <li>1、可上传文件总数；
	 * <li>2、可上传文件占用存储空间上限，单位：字节。
	 * @return
	 */
	public MultiFileRange getMultiFileRange(){
		for(Range range : getRange().getRangeList()){
			if(range instanceof MultiFileRange)
				return (MultiFileRange) range;
		}
		MultiFileRange range = new MultiFileRange();
		getRange().addRange(range);
		return range;
	}

	/**
	 * 可上传文件数量上限
	 * @return
	 */
	final public int getMaxNum() {
		return this.getMultiFileRange().getMaxFileNum();
	}

	/**
	 * 可上传文件占用存储空间上限，单位：字节
	 * @return
	 */
	final public long getMaxSize() {
		return this.getMultiFileRange().getMaxFileSize();
	}

	/**
	 * 当前已上传文件数量
	 * @return
	 */
	final public int getUploadNum(){
		return this.files.size();
	}
	
	/**
	 * 当前已上传各文件的所占用存储空间大小，单位：字节
	 * @return
	 */
	final public String getUploadSize(){
		String uploadSize = "";
		for(FileBOP itemBop : this.getFiles()){
			if(itemBop.getFile() != null)
				uploadSize += itemBop.getFile().length() + ConstantSplit.COMMA_SPLIT;
		}
		return uploadSize;
	}
		
	public List<FileBOP> getFiles() {
		return files;
	}

	public void setFiles(List<FileBOP> files) {
		this.files = files;
	}
	
	@Override
	public Value getValue() {
		StringBuilder valueStr = new StringBuilder();
		for(FileBOP file : getFiles()){
			valueStr.append(file.getDisplayName()).append("///");
		}
		Value value = new Value();
		value.setValue(valueStr.toString());
		return value;
	}
}
