package com.qeweb.demo.mobile.common;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.qeweb.demo.mobile.bo.DemoMobileBaseBO;
import com.qeweb.demo.mobile.bo.ShopBO;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.DateUtils;

/**
 * shopMsg中存储了巡检人员（当前登录用户），和待巡检的门店信息（来自进店时的条码扫描）.
 * <li> 当巡检人员进店时，应当扫描条码，并使用comeInShop存储门店信息；
 * <li> 当巡检人员离店时，应当进行离店操作，并使用leaveOutShop清除门店信息；
 * <li> 巡检人员最多只能对应一个门店信息（同一时刻仅能巡检一家门店）。
 */
public class LoginShopMsg {
	
	/**
	 * loginMsg中存储了巡检人员（当前登录用户），和待巡检的门店信息（来自进店时的条码扫描）
	 * key: 巡检人员ID, value: 待巡检的门店信息
	 */
	private static Map<Long, ShopBO> shopMsg = new HashMap<Long, ShopBO>();
	
	/**
	 * 巡检人员的进店时间
	 */
	private static Map<Long, Timestamp> timMsg = new HashMap<Long, Timestamp>();
	
	/**
	 * 巡店的业务标识集合，记录门店巡检的6个步骤，在离店时清空bussSign
	 */
	@SuppressWarnings("rawtypes")
	private static Map<Long, Set<Class>> bussSign = new HashMap<Long, Set<Class>>();
	
	/**
	 * 巡检ID，6个巡检业务有共D
	 */
	private static Map<Long, String> comparateMap = new HashMap<Long, String>();
	
	/**
	 * 巡检共分为6部分
	 */
	final private static int COMPARATESTEP = 6;
	
	/**
	 * 离店规则:
	 * 是否全部巡检完成后才可提交, 默认为false, 任意时刻均可离店.
	 * 如果compAll是true, 则必须在经过巡检的6步操作后才能离店.
	 */
	private static boolean leaveOutRule = false;
	
	/**
	 * 进店操作
	 * @param shop
	 */
	@SuppressWarnings("rawtypes")
	final static public void comeInShop(ShopBO shop) {
		Long userId = UserContext.getUserId();
		shopMsg.put(userId, shop);
		timMsg.put(userId, DateUtils.getCurrentTimestamp());
		bussSign.put(userId, new HashSet<Class>());
		comparateMap.put(userId, UUID.randomUUID().toString());
	}
	
	/**
	 * 离店操作
	 */
	final static public void leaveOutShop() {
		Long userId = UserContext.getUserId();
		shopMsg.remove(userId);
		timMsg.remove(userId);
		bussSign.remove(userId);
		comparateMap.remove(userId);
	}
	
	/**
	 * 巡检人员是否已经进店
	 * @return
	 */
	final static public boolean isComeIn() {
		return shopMsg.containsKey(UserContext.getUserId());
	}
	
	/**
	 * 获取进店时间
	 * @return
	 */
	final static public Timestamp getComeInTime() {
		return timMsg.get(UserContext.getUserId());
	}
	
	/**
	 * 获取门店信息
	 * @return
	 */
	final static public ShopBO getComeInShopInfo() {
		return shopMsg.get(UserContext.getUserId());
	}

	/**
	 * 每巡检过一个业务后，调用comparate，记录巡检标识
	 */
	final static public void comparate(Class<? extends DemoMobileBaseBO> clasz) {
		bussSign.get(UserContext.getUserId()).add(clasz);
	}
	
	/**
	 * 获取巡检ID
	 * @return
	 */
	final static public String getComparateId() {
		return comparateMap.get(UserContext.getUserId());
	}
	
	/**
	 * 是否巡店结束
	 */
	final static public boolean isComparateOver() {
		return bussSign.get(UserContext.getUserId()).size() == COMPARATESTEP;
	}
	
	/**
	 * 在一个巡检周期结束后，清空所有loginMsg信息
	 */
	final static public void clearAllMsg() {
		shopMsg.clear();
		timMsg.clear();
		bussSign.clear();
		comparateMap.clear();
	}
	
	/**
	 * 离店规则:
	 * 是否全部巡检完成后才可提交, 默认为false, 任意时刻均可离店.
	 * 如果compAll是true, 则必须在经过巡检的6步操作后才能离店.
	 */
	final static public boolean canLeaveShopBeforeCompAll() {
		return leaveOutRule;
	}
	
	final static public void setCompAll(boolean leaveOutRule) {
		LoginShopMsg.leaveOutRule = leaveOutRule;
	}
}
