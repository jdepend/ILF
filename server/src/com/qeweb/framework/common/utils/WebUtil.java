package com.qeweb.framework.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebUtil {
	protected static final Log log = LogFactory.getLog(WebUtil.class);

	/** @deprecated */
	public static void prepareExcelExport(HttpServletResponse response,
			String outputFileName) {
		response.setCharacterEncoding("UTF-8");

		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-disposition", "attachment;filename="
				+ outputFileName);
	}

	public static void prepareExcelExport(HttpServletRequest request,
			HttpServletResponse response, String outputFileName) {
		prepareFileExport(request, response, "application/vnd.ms-excel",
				outputFileName);
	}

	public static void prepareXmlExport(HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
	}

	public static void prepareBinaryExport(HttpServletRequest request,
			HttpServletResponse response, String outputFileName) {
		prepareFileExport(request, response, "application/octet-stream",
				outputFileName);
	}

	public static void prepareFileExport(HttpServletRequest request,
			HttpServletResponse response, String contentType,
			String outputFileName) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);

		String encodedFileName = getEncodedFileName(request, outputFileName);
		if (log.isDebugEnabled()) {
			log.debug("::: [prepareFileExport]encodedFileName->"
					+ encodedFileName);
		}
		response.addHeader("Content-Disposition", "attachment;filename="
				+ encodedFileName);
	}

	public static String getEncodedFileName(HttpServletRequest request,
			String fileName) {
		if (fileName == null) {
			return null;
		}
		String encodedOutputFileName = fileName;
		String agent = request.getHeader("user-agent");
		if (log.isDebugEnabled()) {
			log.debug("::: user-agent for browser->" + agent);
		}
		if (agent != null) {
			try {
				if ((agent.indexOf("Firefox") >= 0)
						|| (agent.indexOf("Gecko") >= 0)) {
					encodedOutputFileName = new String(
							fileName.getBytes("UTF-8"), "ISO-8859-1");
				} else if (agent.indexOf("MSIE") >= 0) {
					encodedOutputFileName = URLEncoder
							.encode(fileName, "UTF-8");
				}
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}
		return encodedOutputFileName;
	}

	public static void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
	}

	public static String getRealPath(HttpServletRequest request,
			String relativePath) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("");

		if (StringUtils.isNotEmpty(relativePath)) {
			if (relativePath.charAt(0) != '/') {
				return realPath + "/" + relativePath;
			}
			return realPath + relativePath;
		}

		return realPath;
	}
}
