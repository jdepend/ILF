package com.qeweb.framework.bc.sysbop.mobilebop;

import com.qeweb.framework.bc.BOProperty;

/**
 * 
 * 终端语音识别Bop 
 */
public class SoundRecognize extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2500156861343685170L;

	@Override
	public String getDesirousMethod(){
		return "getRecognizer";
	}
}
