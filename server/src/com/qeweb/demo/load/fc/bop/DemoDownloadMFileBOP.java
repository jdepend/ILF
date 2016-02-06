package com.qeweb.demo.load.fc.bop;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;

/**
 * 多附件
 */
public class DemoDownloadMFileBOP extends MultiFileBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4191484117777691396L;

	@Override
	public void init() {
		super.init();
		List<FileBOP> files = new ArrayList<FileBOP>();
		for(int i = 1; i <= 10; i++) {
//			FileBOP f = new FileBOP();
//			f.setPath("http://apache.etoak.com/ant/binaries/apache-ant-1.8.2-bin.zip" + i);
//			f.setDisplayName("file" + i);
			files.add(new FileBOP("Chrysanthemum.jpg", "/UploadFiles/20140403/1396516619054.txt", null));
		}
		
		setFiles(files);
	}
}
