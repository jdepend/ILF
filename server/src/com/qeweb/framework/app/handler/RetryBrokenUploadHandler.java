package com.qeweb.framework.app.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.frameworkbo.RetryBrokenUploadBO;

/**
 * 
 * 断点续传处理
 */
public class RetryBrokenUploadHandler {

	final static public String UPLOAD_OK = "ok";
	final static public String UPLOAD_ERR = "err";
	
	/**
	 * 文件上传前, 获取断点续传的文件
	 * @param bo
	 * @param filePath
	 * @param fileId
	 * @throws FileNotFoundException
	 */
	final static public RandomAccessFile getFileSave(RetryBrokenUploadBO bo, String filePath, String fileId)
			throws FileNotFoundException {

		RandomAccessFile oSavedFile = null;

		// 第一次上传
		if (bo == null) {
			try {
				oSavedFile = new RandomAccessFile(getSaveDir(filePath) + File.separator + fileId, "rw");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 续传
		else {
			getSaveDir(filePath.substring(0, filePath.lastIndexOf(File.separator)));
			oSavedFile = new RandomAccessFile(filePath, "rw");
		}

		return oSavedFile;
	}
	
	/**
	 * 文件上传
	 * @param oSavedFile
	 * @param is
	 * @param beginindex 文件起始位置
	 * @param endindex	   文件结束位置
	 * @return 文件上传结果   成功:UPLOAD_OK  失败 UPLOAD_ERR
	 * @throws IOException
	 */
	final static public String upload(RandomAccessFile oSavedFile, InputStream is, long beginindex, long endindex) {
		// 定位文件指针到 nPos 位置
		byte[] b = new byte[1024];
		int nRead;
		// 从输入流中读入字节流，然后写到文件中
		try {
			oSavedFile.seek(beginindex);
		
			while (beginindex < endindex && (nRead = is.read(b, 0, 1024)) > 0) {
				beginindex += nRead; 
				oSavedFile.write(b, 0, nRead);
			}
		} catch (IOException e) {
			return UPLOAD_ERR;
		} finally {
			try {
				is.close();
				oSavedFile.close();
			} catch (IOException e) {
				return UPLOAD_ERR;
			}
		}
		
		return UPLOAD_OK;
	}

	/**
	 * 文件上传后,存储文件状态
	 * @param bo
	 * @param fieldId
	 * @param endindex
	 * @param fileSize
	 */
	final static public void saveUploadState(RetryBrokenUploadBO bo, String fieldId, long endindex, long fileSize) {
		try {
			if(bo == null ) {
				bo = new RetryBrokenUploadBO();
				bo.setFileId(fieldId);
				bo.setFilePosition(endindex);
				bo.setFileSize(fileSize);
				bo.setFileUrl(FileHandleHelp.getFileURLPath(fieldId));
				bo.insert();
			}
			else {
				bo.setFilePosition(endindex);
				bo.getDao().update(bo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得文件所在的目录, 如果没有就创建一个
	 * @param filePath
	 * @return File
	 */
	private static File getSaveDir(String filePath) {
		File savedir = new File(filePath, "rw");
		if (!savedir.exists())
			savedir.mkdirs();
		
		return savedir;
	}
	
	/**
	 * 取得RetryBrokenUploadBO
	 * @param fileId 文件的唯一标识,用时间戳表示
	 * @return RetryBrokenUploadBO
	 */
	final static public RetryBrokenUploadBO getRetryBrokenUploadBO(String fileId) {
		DetachedCriteria dc = DetachedCriteria.forClass(RetryBrokenUploadBO.class);
		dc.add(Restrictions.eq("fileId", fileId));
		RetryBrokenUploadBO result = (RetryBrokenUploadBO) BaseDaoInfo.getDao().get(dc);
		
		return result;
	}
}
