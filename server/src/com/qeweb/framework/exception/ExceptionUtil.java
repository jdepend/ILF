package com.qeweb.framework.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.bc.BusinessComponent;

public class ExceptionUtil {

	final static public void handle(BusinessComponent bc, Exception e) {
		e.printStackTrace();
		if(bc != null) {
			Log log = LogFactory.getLog(bc.getClass());
			log.error(e.getMessage(), e);
		}
	}
}
