package com.qeweb.framework.app.action;

import java.io.File;
import java.io.IOException;

import com.qeweb.framework.app.handler.IFileHandler;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.file.FileUtil;

/**
 * 处理文件上传
 * 
 * 
 */
public class ImgUploadAC extends BaseAction implements IFileHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -737458788387688912L;
	
	private String attachmentFileName; 			//附件名称
	private final int MAX_SIZE = 1024 * 100;	//大小限制: 100KB
	private final String MAX_SIZE_MSG = "100KB";

	@Override
	public String upload() throws IOException {
		getResponse().setContentType(Constant.CONTENTTYPE);
		if(!FileUtil.isFile(getAttachmentFileName())) {
			return null;
		}
		
		File file = new File(getAttachmentFileName());
		if(file.length() > MAX_SIZE) {
			getResponse().getWriter().write("{success:false,msg:'" 
					+ AppLocalization.getLocalization("file.upload.mulipart.maxSize") + MAX_SIZE_MSG + "'}");
			return null;
		}
//		else if(!getAttachmentFileName().toLowerCase().endsWith(".jpg") && getAttachmentFileName().toLowerCase().endsWith(".gif")) {
//			getResponse().getWriter().write("{success:false,msg:'只支持上传jpg和gif格式的图片文件'}");
//			return null;
//		}
		
		ImageBOP bop = new ImageBOP();
		bop.setDisplayName(getAttachmentFileName());
		bop.setFile(file);
		if(bop.upload()) {
			String imgUrl = bop.getPath();
			String temp = imgUrl.toUpperCase();
			if(!temp.startsWith("HTTP://") && !temp.startsWith("HTTPS://"))
				imgUrl = Envir.getBaseURL() + "/" + imgUrl;
			
			getResponse().getWriter().write("{success:true,url:'" + imgUrl + "'}");
		}
		else {
			getResponse().getWriter().write("{success:false,msg:'" + AppLocalization.getLocalization("file.upload.message.upload.error") + "'}");
		}
		
		return null;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
}
