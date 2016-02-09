package com.qeweb.framework.pal.parse;

import com.qeweb.framework.pal.PageContextInfo;
import java.io.*;

public class HtmlEngineContextInfo extends PageContextInfo {

	Writer fw = null;
	
	public HtmlEngineContextInfo(String fileName) {
		try {
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public HtmlEngineContextInfo(Writer fw) {
          fw = this.fw;
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
