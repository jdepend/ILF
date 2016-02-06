package com.qeweb.demo.mobile.aop;

import org.aspectj.lang.JoinPoint;

import com.qeweb.demo.mobile.common.LoginShopMsg;

/**
 * 离店代理
 */
public class DemoMobileLeaveShopAspect {

	/**
     * 离店处理
     * @param jp
     */
    public void leaveOutShop(JoinPoint jp) {
    	LoginShopMsg.leaveOutShop();
    }  
}
