package com.qeweb.framework.frameworkbo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * @see VersionBO 
 */
public class VersionUtil {
	private static VersionUtil versionUtil = null;
	
	private String projectVersion;			//项目版本号
	private String projectVersionTime;		//项目版本时间
	private String qewebVersion;			//开发平台版本号
	private String busVersion;				//业务平台版本号
	private String person;					//填表人
	
	@SuppressWarnings("resource")
	private VersionUtil(){
		BufferedReader reader = null;
		try {
			File versionFile = new File(Envir.getHome() + "version.txt");
			reader = new BufferedReader(new FileReader(versionFile));
			String line;
			while((line = reader.readLine()) != null) {
				String[] array = StringUtils.split(StringUtils.trim(line), ":");
				if(StringUtils.isEmpty(array))
					return;
				
				if(StringUtils.isEqualIgnoreCase("qewebVersion", array[0]) && array.length == 2)
					qewebVersion = array[1];
				else if(StringUtils.isEqualIgnoreCase("busVersion", array[0]) && array.length == 2)
					busVersion = array[1];
				else if(StringUtils.isEqualIgnoreCase("projectVersion", array[0]) && array.length == 2)
					projectVersion = array[1];
				else if(StringUtils.isEqualIgnoreCase("person", array[0]) && array.length == 2)
					projectVersion = array[1];
			}
			reader.close();
			
			Date modifiedTime = new Date(versionFile.lastModified());
			projectVersionTime = DateUtils.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static VersionUtil getInstance(){
		if(versionUtil == null)
			return new VersionUtil();
		else 
			return versionUtil;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public String getProjectVersionTime() {
		return projectVersionTime;
	}

	public String getQewebVersion() {
		return qewebVersion;
	}
	public String getPerson() {
		return person;
	}

	public String getBusVersion() {
		return busVersion;
	}
	
}
