package com.qeweb.busplatform.common.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.MatcherUtil;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 单据流水号bo
 */
public class BP_BillNoBO extends BusinessObject {

	private static final long serialVersionUID = 5667715154729966498L;

	private final static Logger log = Logger.getLogger(BP_BillNoBO.class);

	private String keyName; // 流水号键名称，以此区分不同单号
	private String nextValue; // 流水号下一个值

	public synchronized String gainNextSequenceNo() {
		String prefix = "M"; // 默认单据前缀
		Integer fillLen = Integer.valueOf(3); // 流水号长度
		String formatStr = DateUtils.DATE_FORMAT_YYYYMMDD; // 格式化时间

		String busParamValue = BusSettingConstants.getSequenceNOFormat();
		String paramValue = (StringUtils.isEmpty(busParamValue) ||
				busParamValue.split("%").length < 3) ? "M&yyyyMMdd&3" : busParamValue;

		String[] paramValueArr = paramValue.split("%");
		prefix = paramValueArr[0];
		formatStr = paramValueArr[1];
		fillLen = StringUtils.convertToInteger(paramValueArr[2]);

		String dateStr = DateUtils.dateToString(new Date(), formatStr);
		try {
			keyName = BusSettingConstants.PN_DELIVERYNO;
			this.setKeyName(keyName);
			String sequenceNo = getSequenceDuringDate(dateStr, "%", StringUtils.duplicate(fillLen.intValue(), '_'),
					fillLen.intValue(), keyName);
			this.setNextValue(sequenceNo);
			this.setCreateTime(new Timestamp(new Date().getTime()));
			getDao().save(this);
			return prefix + sequenceNo;
		} catch (HibernateException e) {
			log.error("Could not get sequenceNo for ->" + dateStr + ",reason->" + e.getMessage(), e.getCause());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 得到序列号
	 *
	 * @param date
	 *            当前时间yyyy-mm-dd
	 * @param prefix
	 * @param suffix
	 * @param sequenceLen
	 * @param keyName
	 * @return
	 * @throws HibernateException
	 */
	@SuppressWarnings("rawtypes")
	public synchronized String getSequenceDuringDate(String date, String prefix, String suffix, int sequenceLen,
			String keyName) {
		String resultStr = "";

		try {
			String hql = "select po.nextValue from BP_BillNoBO po where po.keyName =? and "
					+ "po.nextValue like ? order by po.nextValue desc";
			// 在此加上前缀+071215(日期)+后缀
			// // 默认前缀为%071215____这种字样
			List tempList = getDao().findBySql(hql, new Object[]{keyName, prefix + date + suffix});

			// // 得到后三位的list
			List resultList = getValidValueList(tempList, sequenceLen);
			// 对结果进行遍历
			if (resultList != null && resultList.size() > 0) {
				int max = getMaxValue(resultList);
				Integer newCd = Integer.valueOf(max + 1);
				String newBackThreeBit = toString(newCd, sequenceLen, '0');
				resultStr = date + newBackThreeBit;
			} else {
				resultStr = date + StringUtils.duplicate(sequenceLen - 1, '0') + "1";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return resultStr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List getValidValueList(List tempList, int sequenceLen) {
		if(ContainerUtil.isNull(tempList))
			return null;

		List tempResultList = null;
		tempResultList = new ArrayList();
		for (Iterator iter = tempList.iterator(); iter.hasNext();) {
			String sequenceNo = (String) iter.next();
			if(StringUtils.isEmpty(sequenceNo))
				continue;

			String backThreeBit = sequenceNo.substring(sequenceNo.trim().length() - sequenceLen);
			if (MatcherUtil.isNumber(backThreeBit)) {
				tempResultList.add(backThreeBit);
			}
		}
		return tempResultList;
	}

	/**
	 *
	 * @param resultList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static int getMaxValue(List resultList) {
		String first = (String) resultList.get(0);
		int max = Integer.parseInt(first);
		for (int i = 1; i < resultList.size(); i++) {
			String sequenceNo = (String) resultList.get(i);
			int oldBackThreeBit = Integer.parseInt(sequenceNo);
			if (oldBackThreeBit > max) {
				max = oldBackThreeBit;
			}
		}
		return max;
	}

	/**
	 *
	 * @param value
	 * @param len
	 * @param fillChar
	 * @return
	 */
	private static String toString(Integer value, int len, char fillChar) {
		if (value == null) {
			return StringUtils.duplicate(len, fillChar);
		}
		String tmp = String.valueOf(value);
		String ret = null;
		if (tmp.length() < len)
			ret = StringUtils.duplicate(len - tmp.length(), fillChar) + tmp;
		else {
			ret = tmp.substring(tmp.length() - len);
		}
		return ret;
	}

	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getNextValue() {
		return nextValue;
	}
	public void setNextValue(String nextValue) {
		this.nextValue = nextValue;
	}

}
