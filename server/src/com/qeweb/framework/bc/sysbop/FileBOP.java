package com.qeweb.framework.bc.sysbop;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.bc.prop.FileRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantURL;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 文件操作BOP, 文件上传、下载控件绑定该BOP
 */
public class FileBOP extends BOProperty {
	private static final long serialVersionUID = -6441104470944375659L;
	private static final Log log = LogFactory.getLog(FileBOP.class);
	/**
	 * 文件显示名称(含扩展名)
	 */
	private String displayName;		
	/**
	 * 上传文件在服务器中存储路径
	 */
	private String path;
	private File file;
	/**
	 * 前次上传的文件在存储在服务器中存储路径;<br>
	 * 例如先上传1.txt，然后又上传2.txt，则该属性的值为1.txt的存储路径.
	 * lastPath可用于删除上次上传的文件.
	 */
	private String lastPath;
	
	public FileBOP(){
	}
	
	/**
	 * @param displayName 文件显示名称(含扩展名)，可以为空，默认值为“下载”
	 * @param path 上传文件在服务器中存储路径，必填
	 * @param file 上传文件，可以为空
	 */
	public FileBOP(String displayName, String path, File file){
		this.setDisplayName(displayName);
		this.setPath(path);
		this.setFile(file);
	}
	
	@Override
	public void init() {
		FileRange range = new FileRange();
		getRange().addRange(range, LogicEnum.AND);
		this.setRange(getRange());
	}
	
	/**
	 * 服务器端文件下载路径: Envir.getBaseURL() + "/" + ConstantURL.CONTAINER_DOWNLOAD + path
	 */
	@Override
	public String toLink() {
		if(StringUtils.isEmpty(path))
			return "";
				
		String temp = path.toUpperCase();
		if(temp.startsWith("HTTP://") || temp.startsWith("HTTPS://"))
			return getPath();
		
		String link = Envir.getBaseURL() + "/" + ConstantURL.CONTAINER_DOWNLOAD;
		try {
			link += URLEncoder.encode(URLEncoder.encode(getPath(), Constant.ENCODING_UTF8), Constant.ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			link += getPath();
		}
		//使用struts下载
		return link;
	}
	
	@Override
	public String toImage() {
		if(StringUtils.isEmpty(path))
			return "";
				
		String temp = path.toUpperCase();
		if(temp.startsWith("HTTP://") || temp.startsWith("HTTPS://"))
			return getPath();
		
		return Envir.getBaseURL() + getPath();
	}
	
	/**
	 * 文件上传, <qeweb:fileFiled> 标签默认执行的方法,
	 * 如果在标签中添加operate属性, 则执行operate属性指代的方法.
	 * @return
	 */
	public boolean upload(){		
		try {
			//保存目录名
			String uploadDirName = FileHandleHelp.getFilePath(getCustomDir());
			//保存文件名
			String saveFileName = FileHandleHelp.getFileSaveName(displayName);
			File savefile = new File(FileHandleHelp.makdSaveDir(uploadDirName), saveFileName);		
			FileUtils.copyFile(file, savefile);
			this.path = FileHandleHelp.getFileURLPath(saveFileName, uploadDirName);
			
			return true;
		} catch (IOException e) {			
			log.error(e.toString());
			return false;
		}
	}
	
	/**
	 * <ul>自定义上传文件存储路径
	 * <li>单层文件夹形式 mydir
	 * <li>多层文件夹 形式mydir1/mydir2
	 * @return - a string.
	 */
	protected String getCustomDir(){
		return "";
	}
	
	/**
	 * 设置文件上传操作的最终返回值
	 * @param result
	 */
	@SuppressWarnings("deprecation")
	final public void setResult(boolean result){
		if(result){
			if(result != this.getSuccess())
				this.setSuccessMessage("");
		}
		else if(result != this.getSuccess()){
			this.setErrorMessage("");
		}
	}

	/**
	 * 获取文件最大允许上传大小
	 * @return
	 */
	final public long getMaxSize() {
		return this.getFileRange().getMaxSize();
	}
	
	/**
	 * 获取允许上传类型
	 * @return
	 */
	final public Set<String> getAllowedType(){
		return this.getFileRange().getAllowedType();
	}
	
	/**
	 * 获取不允许上传类型
	 * @return
	 */
	final public Set<String> getNotAllowedType(){
		return this.getFileRange().getNotAllowedType();
	}
	
	/**
	 * 获取上传文件限制
	 * @return
	 */
	public FileRange getFileRange(){
		for(Range range: getRange().getRangeList()){
			if(range instanceof FileRange)
				return (FileRange) range;
		}
		FileRange range = new FileRange();
		getRange().addRange(range, LogicEnum.AND);
		return range;
	}
		
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		setValue(displayName);
	}

	public String getDisplayName() {
		return StringUtils.isNotEmpty(displayName) ? 
				displayName : AppLocalization.getLocalization("down");
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		
		if(StringUtils.isNotEmpty(path)){
			if(path.indexOf(Envir.getBaseURL()) != -1)
				path = path.replace(Envir.getBaseURL(), "");
			if(path.indexOf("/" + ConstantURL.CONTAINER_DOWNLOAD) != -1)
				path = path.replace("/" + ConstantURL.CONTAINER_DOWNLOAD, "");
			try {
				path = URLDecoder.decode(URLDecoder.decode(
						URLDecoder.decode(path, Constant.ENCODING_UTF8), Constant.ENCODING_UTF8), 
						Constant.ENCODING_UTF8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			path = path.replace("\\", "/");
		}
		return StringUtils.getUnescapedText(path);
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		if(file == null && StringUtils.isNotEmpty(getPath()))
			file = new File(Envir.getContext().getRealPath("/" + getPath()));
		return file;
	}

	public void setLastPath(String lastPath) {
		this.lastPath = lastPath;
	}

	public String getLastPath() {
		return lastPath;
	}

	/**
	 * 清空附件信息
	 */
	public void clear() {
		displayName = null;
		path = null;
		file = null;
		lastPath = null;
	}
	
	@Override
	public void free() {
		clear();
		super.free();
	}
}
