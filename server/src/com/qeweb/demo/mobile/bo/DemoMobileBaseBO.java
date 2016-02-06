package com.qeweb.demo.mobile.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.qeweb.demo.mobile.bop.SubmitBOP;
import com.qeweb.demo.mobile.common.LoginShopMsg;
import com.qeweb.demo.mobile.dao.ia.IDemoMobileBaseDao;
import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.ImageBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.LocationBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.PictureBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 移动巡检基础BO
 */
public class DemoMobileBaseBO extends BusinessObject {

	private static final long serialVersionUID = 5896507444629158416L;

	private Timestamp comeInTime;		//巡检时间
	private Timestamp leaveOutTime;		//离店时间
	private ShopBO shopBO;				//门店信息
	private int submitFlag;				//提交状态
	private UserBO visitor;				//巡店员
	private String comparateId;			//巡检ID

	private String picture;				//照片路径
	private FileBOP picFile;
	private String locationStr;			//从终端获取的定位信息(gps或cellID信息)
	private LocationBOP location;		//地址信息

	private IDemoMobileBaseDao demoMobileBaseDao;

	public DemoMobileBaseBO() {
		super();
		addBO("shopBO", new ShopBO());
		addBOP("picture", new PictureBOP());
		addBOP("locationStr", new LocationBOP());
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(bot == null)
			bot = new BOTemplate();

		if(StringUtils.isEqual("ShopBO", bot.getBoName())) {
			String shopId = (String)bot.getValue("id");
			bot = new BOTemplate();
			bot.push("shopBO.id", shopId);
		}

		bot.push("submitFlag", SubmitBOP.YES);
		return super.query(bot, start);
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			DemoMobileBaseBO bo = (DemoMobileBaseBO) page.getItems().get(i);
			//经纬度信息
			if(bo.getLocation() != null)
				bo.setLocationStr(bo.getLocation().getAddress());
			BOHelper.initPreferencePage_lazy(bo, this);
			boList.add(bo);
		}

		page.setBOList(boList);
	}

	/**
	 * 加载基础信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public DemoMobileBaseBO loadInfo() throws Exception {
		ShopBO shop = LoginShopMsg.getComeInShopInfo();

		DemoMobileBaseBO result = getDemoMobileBaseDao().findSaveInfo(shop.getId(), getUserId(), getSearchClass());
		if(result.getId() == 0) {
			result.setComeInTime(LoginShopMsg.getComeInTime());
			result.setLeaveOutTime(DateUtils.getCurrentTimestamp());
		}
		result.setShopBO(shop);
		BOHelper.initPreferencePage(result);

		return result;
	}

	/**
	 * 保存巡检信息
	 * @param bo
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void save(DemoMobileBaseBO bo) throws Exception {
		ShopBO shop = LoginShopMsg.getComeInShopInfo();
		PropertyUtils.copySamePropertyWithOutNull(this, bo);
		DemoMobileBaseBO oldBO = getDemoMobileBaseDao().findSaveInfo(shop.getId(), getUserId(), getSearchClass());
		bo.setId(oldBO.getId());

		PropertyUtils.copyPropertyWithOutNull(oldBO, this);
		if(this.getId() == 0L) {
			BOHelper.setBOPublicFields_insert(this);
			setSubmitFlag(StringUtils.convertToInt(SubmitBOP.NO));
			setComeInTime(LoginShopMsg.getComeInTime());
			setPicture(FileHandleHelp.getFileURLPath(this.getPicture()));

			LocationBOP bop = (LocationBOP) getBOP("locationStr");
			bop.setLocation(bo.getLocationStr());
			setLocation(bop.insert());
			setVisitor(getUserBO());
			this.setShopBO(shop);
			this.setComparateId(LoginShopMsg.getComparateId());
			getDao().saveOrUpdate(this);
		}
		else {
			BOHelper.setBOPublicFields_update(this);
			//处理重新拍摄的照片
			if(StringUtils.isNotEmpty(this.getPicture())) {
				oldBO.setPicture(FileHandleHelp.getFileURLPath(this.getPicture()));
				oldBO.setComparateId(LoginShopMsg.getComparateId());
			}
			getDao().saveOrUpdate(oldBO);
		}

		LoginShopMsg.comparate(getSearchClass());
	}

	/**
	 * 提交巡检数据
	 * @param bo
	 * @throws Exception
	 */
	public void submit(long shopId) throws Exception {
		DemoMobileBaseBO mobileBaseBO = (DemoMobileBaseBO)getDemoMobileBaseDao().findSaveInfo(shopId, getUserId(), this.getClass());
		if(mobileBaseBO != null && mobileBaseBO.getId() != 0) {
			mobileBaseBO.setSubmitFlag(StringUtils.convertToInt(SubmitBOP.YES));
			mobileBaseBO.update();
		}
	}

	/**
	 * 查看明细
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public DemoMobileBaseBO showDesc(BusinessObject bo) throws Exception {
		DemoMobileBaseBO result = null;
		String comparateId = ((DemoMobileBaseBO)bo).getComparateId();
		if(StringUtils.isNotEmpty(comparateId))
			result = getDemoMobileBaseDao().find(comparateId, getSearchClass());
		else
			result = (DemoMobileBaseBO) getRecord(bo.getId());

		BOHelper.initPreferencePage(result);
		ImageBOP imgBOP = new ImageBOP(null, result.getPicture(), null);
		imgBOP.setHeight("600");
		result.addBOP("picFile", imgBOP);

		return result;
	}

	/**
	 * TODO 终端获取session出现问题，此处暂时写成固定BO
	 * @return
	 */
	private UserBO getUserBO() {
		return UserContext.getUserBO();
	}

	private long getUserId() {
		return UserContext.getUserId();
	}

	public Timestamp getComeInTime() {
		return comeInTime;
	}

	public void setComeInTime(Timestamp comeInTime) {
		this.comeInTime = comeInTime;
	}

	public Timestamp getLeaveOutTime() {
		return leaveOutTime;
	}

	public void setLeaveOutTime(Timestamp leaveOutTime) {
		this.leaveOutTime = leaveOutTime;
	}

	public int getSubmitFlag() {
		return submitFlag;
	}

	public void setSubmitFlag(int submitFlag) {
		this.submitFlag = submitFlag;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public FileBOP getPicFile() {
		return picFile;
	}

	public void setPicFile(FileBOP picFile) {
		this.picFile = picFile;
	}

	public String getLocationStr() {
		return locationStr;
	}

	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}

	public LocationBOP getLocation() {
		return location;
	}

	public void setLocation(LocationBOP location) {
		this.location = location;
	}

	public UserBO getVisitor() {
		return visitor;
	}

	public void setVisitor(UserBO visitor) {
		this.visitor = visitor;
	}


	public IDemoMobileBaseDao getDemoMobileBaseDao() {
		if(demoMobileBaseDao == null)
			demoMobileBaseDao = (IDemoMobileBaseDao)SpringConstant.getCTX().getBean("demoMobileBaseDao");
		return demoMobileBaseDao;
	}

	public void setDemoMobileBaseDao(IDemoMobileBaseDao demoMobileBaseDao) {
		this.demoMobileBaseDao = demoMobileBaseDao;
	}

	@Override
	public IBaseDao getDao() {
		return getDemoMobileBaseDao();
	}

	public ShopBO getShopBO() {
		return shopBO;
	}

	public void setShopBO(ShopBO shopBO) {
		this.shopBO = shopBO;
	}

	public String getComparateId() {
		return comparateId;
	}

	public void setComparateId(String comparateId) {
		this.comparateId = comparateId;
	}


}
