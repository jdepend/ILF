package com.qeweb.framework.manager;


/**
 * VCManager
 *
 */
public class VCManager {

	/**
	 * 获取view component 工厂
	 * @return
	 */
	final static public VCFactory getVCFactory() {
		if(DisplayType.isExt())
			return new ExtFactory();
		else if(DisplayType.isHtml())
			return new HtmlFactory();
        else if(DisplayType.isBootstrap())
            return new BootstrapFactory();
        else if(DisplayType.isAndroid())
			return new AndroidFactory();
		else if(DisplayType.isPad())
			return new PadFactory();
		else if(DisplayType.isIphone())
			return new AndroidFactory();
		else
			return new ExtFactory();
	}

	/**
	 * 获取view component 工厂
	 * @param displayType
	 * @return
	 */
	final static public VCFactory getVCFactory(String displayType) {
		if(DisplayType.isExt(displayType))
			return new ExtFactory();
		else if(DisplayType.isHtml(displayType))
			return new HtmlFactory();
		else if(DisplayType.isAndroid(displayType))
			return new AndroidFactory();
		else if(DisplayType.isPad(displayType))
			return new PadFactory();
		else if(DisplayType.isIphone(displayType))
			return new AndroidFactory();
		else
			return new ExtFactory();
	}
	
	/**
	 * 获取view component Class 工厂
	 * @return
	 */
	final static public VCClassFactory getVCClassFactory() {
		if(DisplayType.isExt())
			return new ExtClassFactory();
		else if(DisplayType.isHtml())
			return new HtmlClassFactory();
		else if(DisplayType.isAndroid())
			return new AndroidClassFactory();
		else if(DisplayType.isPad())
			return new PadClassFactory();
		else if(DisplayType.isIphone())
			return new AndroidClassFactory();
		else
			return new ExtClassFactory();
	}
}
