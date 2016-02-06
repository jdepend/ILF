package com.qeweb.framework.app.servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.app.handler.RetryBrokenUploadHandler;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbo.RetryBrokenUploadBO;

/**
 * 断点续传servlet, 用于实现断点续传<br>
 *
 */
public class RetryBrokenUploadServlet extends HttpServlet {
       
	private static final long serialVersionUID = 7396351646721874586L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataInputStream dis = new DataInputStream(request.getInputStream());
		String fileId = dis.readUTF();
		//每次读取的起始点
		long begin = dis.readLong();
		//每次读取的截止点
		long end = dis.readLong();
		//文件大小
		long fileSize = dis.readLong();
		
		RetryBrokenUploadBO bo = RetryBrokenUploadHandler.getRetryBrokenUploadBO(fileId);
		
		//文件上传前,获取断点续传的文件
		RandomAccessFile oSavedFile = RetryBrokenUploadHandler.getFileSave(bo, getFilePath(bo, request), fileId);
		
		//文件上传,将上传的结果(成功或失败)写入response
		String result = RetryBrokenUploadHandler.upload(oSavedFile, request.getInputStream(), begin, end);
		response.getWriter().print(result);
		
		//文件上传后,存储文件状态
		if(StringUtils.isEqual(RetryBrokenUploadHandler.UPLOAD_OK, result)) {
			RetryBrokenUploadHandler.saveUploadState(bo, fileId, end, fileSize);
		}
	}


	/**
	 * 获取断点续传的文件路径
	 * @param bo
	 * @param request
	 * @return
	 */
	private String getFilePath(RetryBrokenUploadBO bo, HttpServletRequest request) {
		if(bo == null) 
			return request.getSession().getServletContext().getRealPath("/" + FileHandleHelp.getFilePath());
		else 
			return request.getSession().getServletContext().getRealPath(bo.getFileUrl());
	}
}
