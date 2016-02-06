package com.qeweb.framework.app.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qeweb.framework.app.handler.RetryBrokenUploadHandler;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.frameworkbo.RetryBrokenUploadBO;

/**
 * 断点续传servlet, 用于获取文件上传的起始位置<br>
 * 如: 文件工10000bytes, 从500bytes开始上传
 *
 */
public class RetryBrokenUploadAskServlet extends HttpServlet {
       
	private static final long serialVersionUID = -3634981958769524491L;
	private static final long FILE_UNDEFINED = 0L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public RetryBrokenUploadAskServlet() {
        super();
    }

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
		response.getWriter().print(getFilePosition(request));
	}
	
	/**
	 * 从数据库读取文件传输起始位置,如: 文件工10000bytes, 从500bytes开始上传
	 * <br>
	 * 如果数据库无记录，返回FILE_UNDEFINED;如果有记录，返回已传输的长度
	 * @param request
	 * @return
	 */
	private long getFilePosition(HttpServletRequest request) {
		RetryBrokenUploadBO bo = RetryBrokenUploadHandler.getRetryBrokenUploadBO(
				request.getParameter(ConstantParam.BROKENDOWNLOAD_FIELDID));
		
		if (bo == null) {
			return FILE_UNDEFINED;
		}
		else {
			File savedir = new File(request.getSession().getServletContext().getRealPath(bo.getFileUrl()));
			if (!savedir.exists())
				return FILE_UNDEFINED;
			else 
				return bo.getFilePosition();
		}
	}
}
