package com.qeweb.framework.pl.common.parse;

import com.qeweb.framework.pal.PageContextInfo;

import java.io.*;

public class WriterEngineContextInfo extends PageContextInfo {

	Writer fw = null;

    public WriterEngineContextInfo(Writer fw) {
        this.fw = fw;
    }

	public void close() throws Exception{
		fw.flush();
		fw.close();
	}
	
	@Override
	public Writer getWriterHandle(){
		return fw;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecurityURL(String URI) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
