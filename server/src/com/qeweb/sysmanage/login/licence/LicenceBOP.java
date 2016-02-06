package com.qeweb.sysmanage.login.licence;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.bc.sysbop.FileBOP;

/**
 * licence上传BOP
 */
public class LicenceBOP extends FileBOP {

	private static final long serialVersionUID = 51756390773061563L;

	public boolean upload(){		
		super.upload();
		File savefile = new File(FileHandleHelp.makdSaveDir(""), getDisplayName());
		try {
			FileUtils.copyFile(getFile(), savefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
