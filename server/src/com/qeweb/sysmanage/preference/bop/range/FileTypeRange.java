package com.qeweb.sysmanage.preference.bop.range;

import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;

public class FileTypeRange extends LogicRange {
	private static final long serialVersionUID = 6591239037750671673L;

	@Override
	public boolean isIN(Value value) {
		if(StringUtils.isEmpty(StringUtils.trim(value.getValue())))
			return true;
		if(value.getValue().indexOf("\\") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("/") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf(":") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("*") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("?") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("\"") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("<") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf(">") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(value.getValue().indexOf("|") != -1){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorSymbol"));
			return false;
		}
		if(StringUtils.trim(value.getValue()).startsWith(",")){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorStart"));
			return false;
		}
		if(StringUtils.trim(value.getValue()).endsWith(",")){
			setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorEnd"));
			return false;
		}
		
		if(value.getValue().indexOf(",") != -1)			
			for(String e : StringUtils.split(StringUtils.trim(value.getValue()), ",")){
				if(StringUtils.isEmpty(e)){
					setValidateMessage(AppLocalization.getLocalization("com.qeweb.sysmanage.preference.bop.range.FileTypeRange.errorName"));
					return false;
				}
			}
		return true;
	}

}
