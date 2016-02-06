package com.qeweb.demo.mobile.bo;

import com.qeweb.demo.mobile.common.LoginShopMsg;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * 进店管理
 */
public class ComeInShopBO extends BusinessObject {

	private static final long serialVersionUID = 1100118798081668274L;

	private String shopMsgBarcode;		//门店信息二维码

	public ComeInShopBO() {
		super();
		addBOP("shopMsgBarcode", new NotEmptyBop());
	}
	
	/**
	 * 校验扫描的门店信息是否正确
	 * @throws Exception 
	 */
	public void validateBarcode(ComeInShopBO bo) throws Exception {
		//为扫描门店信息,或扫描失败
//		if(true) {
//			throw new BOException("门店信息有误，请扫描正确的门店信息条码！" + bo.getShopMsgBarcode());
//		}
		
		ShopBO shop = (ShopBO)new ShopBO().getRecord(1L);
		LoginShopMsg.comeInShop(shop);
	}

	public String getShopMsgBarcode() {
		return shopMsgBarcode;
	}

	public void setShopMsgBarcode(String shopMsgBarcode) {
		this.shopMsgBarcode = shopMsgBarcode;
	}
	
	
}
