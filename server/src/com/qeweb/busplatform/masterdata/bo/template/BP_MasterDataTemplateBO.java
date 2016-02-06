package com.qeweb.busplatform.masterdata.bo.template;

import com.qeweb.busplatform.masterdata.template.TemplateCreater;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;

/**
 * 主数据模板管理, 用于生成导入主数据的excel模板
 */
public class BP_MasterDataTemplateBO extends BusinessObject {

	private static final long serialVersionUID = 3287685941432444746L;

	private FileBOP materialTemplate;					//物料模板
	private TemplateCreater meterialTemplateCreater;	//物料excel模板创建者

	private FileBOP vendorTemplate;						//供应商模板
	private TemplateCreater vendorTemplateCreater;		//供应商excel模板创建者

	private FileBOP goodsReciveTemplate;				//收货单模板
	private TemplateCreater goodsReciveTemplateCreater;	//收货单excel模板创建者

	private FileBOP purchaseOrderTemplate;					//采购订单模板
	private TemplateCreater purchaseOrderTemplateCreater;	//采购订单excel模板创建者

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BP_MasterDataTemplateBO template = new BP_MasterDataTemplateBO();
		//物料模版
		template.addBOP("materialTemplate", getMeterialTemplateCreater().createTemplateBOP());
		//供应商模版
		template.addBOP("vendorTemplate", getVendorTemplateCreater().createTemplateBOP());
		//供应商模版
		template.addBOP("goodsReciveTemplate", getGoodsReciveTemplateCreater().createTemplateBOP());
		//采购订单模板
		template.addBOP("purchaseOrderTemplate", getPurchaseOrderTemplateCreater().createTemplateBOP());

		return template;
	}

	/**
	 * 生成模版
	 * @return
	 * @throws Exception
	 */
	public void createTemplate() throws Exception {
		getMeterialTemplateCreater().createTemplate(this);
		getVendorTemplateCreater().createTemplate(this);
		getGoodsReciveTemplateCreater().createTemplate(this);
		getPurchaseOrderTemplateCreater().createTemplate(this);
	}

	public FileBOP getMaterialTemplate() {
		return materialTemplate;
	}

	public void setMaterialTemplate(FileBOP materialTemplate) {
		this.materialTemplate = materialTemplate;
	}

	public TemplateCreater getMeterialTemplateCreater() {
		return meterialTemplateCreater;
	}

	public void setMeterialTemplateCreater(TemplateCreater meterialTemplateCreater) {
		this.meterialTemplateCreater = meterialTemplateCreater;
	}

	public FileBOP getVendorTemplate() {
		return vendorTemplate;
	}

	public void setVendorTemplate(FileBOP vendorTemplate) {
		this.vendorTemplate = vendorTemplate;
	}

	public TemplateCreater getVendorTemplateCreater() {
		return vendorTemplateCreater;
	}

	public void setVendorTemplateCreater(TemplateCreater vendorTemplateCreater) {
		this.vendorTemplateCreater = vendorTemplateCreater;
	}

	public FileBOP getGoodsReciveTemplate() {
		return goodsReciveTemplate;
	}

	public void setGoodsReciveTemplate(FileBOP goodsReciveTemplate) {
		this.goodsReciveTemplate = goodsReciveTemplate;
	}

	public TemplateCreater getGoodsReciveTemplateCreater() {
		return goodsReciveTemplateCreater;
	}

	public void setGoodsReciveTemplateCreater(
			TemplateCreater goodsReciveTemplateCreater) {
		this.goodsReciveTemplateCreater = goodsReciveTemplateCreater;
	}

	public FileBOP getPurchaseOrderTemplate() {
		return purchaseOrderTemplate;
	}

	public void setPurchaseOrderTemplate(FileBOP purchaseOrderTemplate) {
		this.purchaseOrderTemplate = purchaseOrderTemplate;
	}

	public TemplateCreater getPurchaseOrderTemplateCreater() {
		return purchaseOrderTemplateCreater;
	}

	public void setPurchaseOrderTemplateCreater(
			TemplateCreater purchaseOrderTemplateCreater) {
		this.purchaseOrderTemplateCreater = purchaseOrderTemplateCreater;
	}
}
