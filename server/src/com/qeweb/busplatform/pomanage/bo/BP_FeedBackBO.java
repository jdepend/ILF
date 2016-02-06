package com.qeweb.busplatform.pomanage.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.POFeedback;
import com.qeweb.busplatform.pomanage.dao.ia.IBP_FeedBackDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 反馈BO
 */
public class BP_FeedBackBO extends BusinessObject {

	private static final long serialVersionUID = 1687889930710701915L;

	/**
	 * 用于记录反馈单据的类型
	 */
	public static final int PURCHASE_ORDER = 1;			//采购订单主
	public static final int PURCHASE_ORDER_ITEM = 2;	//采购订单明细
	public static final int PURCHASE_GOODS_PLAN = 3;	//供货计划

	private long billId;				//单据ID
	private Integer billType; 			//单据类型
	private String feedbackContent; 	//反馈内容

	private OrganizationBO feedOrgBO; 			//反馈组织
	private OrganizationBO recOrgBO; 			//接收组织
	private UserBO feedUserBO = new UserBO(); 	//反馈用户

	//反馈行为
	private POFeedback OPT_POFeecback;

	private IBP_FeedBackDao feedBackDao;

	public BP_FeedBackBO(){
		super();
		addBOP("feedbackContent", new NotEmptyBop(1, 512));
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		if (StringUtils.isEqual("bp_PurchaseOrderBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("billId", ctxBot.getValue("id"));
				thisBOT.push("billType", PURCHASE_ORDER);
			}
		}
		else if (StringUtils.isEqual("bp_PurchaseOrderItemBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("billId", ctxBot.getValue("id"));
				thisBOT.push("billType", PURCHASE_ORDER_ITEM);
			}
		}
		else if (StringUtils.isEqual("bp_PurchaseGoodsPlanBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("billId", ctxBot.getValue("id"));
				thisBOT.push("billType", PURCHASE_GOODS_PLAN);
			}
		}
		else {
			thisBOT = bot;
		}

		return super.query(thisBOT, start);
	}

	/**
	 * 按创建时间升序排列
	 * @param dc
	 */
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("createTime", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}

	/**
	 * 反馈操作
	 */
	public void feedback(List<BusinessObject> boList) throws Exception {
		getOPT_POFeecback().feedback(boList);
	}

	/**
	 * 获取单据反馈数
	 * @param billId 单据ID
	 * @param billType 单据类别
	 * @return
	 */
	public Integer getfeedbackCount(long billId, Integer billType) {
		return getDao().getfeedbackCount(billId, billType);
	}

	public long getBillId() {
		return billId;
	}
	public void setBillId(long billId) {
		this.billId = billId;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public OrganizationBO getFeedOrgBO() {
		return feedOrgBO;
	}
	public void setFeedOrgBO(OrganizationBO feedOrgBO) {
		this.feedOrgBO = feedOrgBO;
	}
	public OrganizationBO getRecOrgBO() {
		return recOrgBO;
	}
	public void setRecOrgBO(OrganizationBO recOrgBO) {
		this.recOrgBO = recOrgBO;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public UserBO getFeedUserBO() {
		return feedUserBO;
	}

	public void setFeedUserBO(UserBO feedUserBO) {
		this.feedUserBO = feedUserBO;
	}

	public POFeedback getOPT_POFeecback() {
		if(OPT_POFeecback == null)
			OPT_POFeecback = BusOptManager.getOptPOFeedback();

		return OPT_POFeecback;
	}

	public void setOPT_POFeecback(POFeedback oPT_POFeecback) {
		OPT_POFeecback = oPT_POFeecback;
	}

	public IBP_FeedBackDao getDao() {
		return getFeedBackDao();
	}

	public IBP_FeedBackDao getFeedBackDao() {
		if(feedBackDao == null)
			feedBackDao = (IBP_FeedBackDao)SpringConstant.getCTX().getBean("IBP_FeedBackDao");
		return feedBackDao;
	}

	public void setFeedBackDao(IBP_FeedBackDao feedBackDao) {
		this.feedBackDao = feedBackDao;
	}

}