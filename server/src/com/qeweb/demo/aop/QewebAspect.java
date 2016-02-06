package com.qeweb.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class QewebAspect {

	/**
	 * 前置通知
	 * @param jp
	 */
	public void doAfter(JoinPoint jp) {  
        System.out.println("log Ending method: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
    }  
  
    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        long time = System.currentTimeMillis();  
        Object retVal = pjp.proceed();  
        time = System.currentTimeMillis() - time;  
        System.out.println("process time: " + time + " ms");  
        return retVal;  
    }  
  
    /**
     * 后通知
     * @param jp
     */
    public void doBefore(JoinPoint jp) {  
        System.out.println("log Begining method: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
    }  
  
    /**
     * 抛出异常后通知
     * @param jp
     * @param ex
     */
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        System.out.println("method " + jp.getTarget().getClass().getName()  
                + "." + jp.getSignature().getName() + " throw exception");  
        System.out.println(ex.getMessage());  
    } 
}
