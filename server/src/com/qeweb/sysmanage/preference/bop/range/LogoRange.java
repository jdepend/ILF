package com.qeweb.sysmanage.preference.bop.range;

import java.util.HashSet;
import java.util.Set;

import com.qeweb.framework.bc.prop.FileRange;

/**
 * logo图片范围
 */
public class LogoRange extends FileRange {

	private static final long serialVersionUID = -6003601187928676319L;
	
	@Override
	public Set<String> getAllowedType() {
		Set<String> typeSet = new HashSet<String>();
		typeSet.add("jpg");
		typeSet.add("jpeg");
		typeSet.add("png");
		typeSet.add("gif");
		
		return typeSet;
	}
	
	@Override
	public Set<String> getNotAllowedType() {
		Set<String> typeSet = new HashSet<String>();
		return typeSet;
	}

}
