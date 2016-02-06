package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.dao.ia.IVarPageDao;

/**
 * 保存变量与组件的映射关系
 */
public class VarPageAllBO extends BusinessObject {

	private static final long serialVersionUID = 7500773600231554015L;
	
	private IVarPageDao varPageDao;
	
	public VarPageAllBO() {
		super();
		addOperateBOP("goBack", new NOSubmitBOP());
	}
	
	/**
	 * 保存变量与组件的映射关系
	 * @param boList
	 */
	public void save(List<BusinessObject> boList) throws Exception {
		//变量-组件关联信息(关联信息主表)
		VarPageBO varPageBO = null;
		//变量值-组件关联信息
		VarPageItemBO varPageItemBO = null;
		//变量信息
		List<VarConfBO> varConfBOList = new LinkedList<VarConfBO>();
		//受变量信息影响的组件
		List<VarVCBO> varVCBOList = new LinkedList<VarVCBO>();
		
		//修改标识, 如果moifyFlag == true, 表示正在进行修改操作
		boolean moifyFlag = false;
		for(BusinessObject bo : boList) {
			if(bo instanceof VarPageBO) {
				varPageBO = (VarPageBO) bo;
			}
			else if(bo instanceof VarPageItemBO) {
				varPageItemBO = (VarPageItemBO) bo;
				if(varPageItemBO.getId() != 0L)
					moifyFlag = true;
				varPageItemBO.setRelateName(StringUtils.removeAllSpace(varPageItemBO.getRelateName()));
			}
			else if(bo instanceof VarConfBO) {
				varConfBOList.add((VarConfBO) bo);
			}
			else if(bo instanceof VarVCBO) {
				varVCBOList.add((VarVCBO) bo);
			}
		}
		
		validateCommon(varConfBOList, varVCBOList);
		if(moifyFlag) {
			if(validateUpdate(varPageBO.getId(), varPageItemBO, varConfBOList))
				getVarPageDao().updateVarMapping(varPageBO, varPageItemBO, varConfBOList, varVCBOList);
		}
		else {
			if(validateInsert(varPageBO.getId(), varPageItemBO, varConfBOList))
				getVarPageDao().insertVarMapping(varPageBO, varPageItemBO, varConfBOList, varVCBOList);
		}
	}
	
	/**
	 * 保存变量与tab组件的映射关系
	 * @param boList
	 */
	public void saveTab(List<BusinessObject> boList) throws Exception {
		//变量-组件关联信息(关联信息主表)
		VarPageBO varPageBO = null;
		//变量值-组件关联信息
		VarPageItemBO varPageItemBO = null;
		//变量信息
		List<VarConfBO> varConfBOList = new LinkedList<VarConfBO>();
		//sheet页信息
		List<SheetBO> sheetBOList = new LinkedList<SheetBO>();
		
		//修改标识, 如果moifyFlag == true, 表示正在进行修改操作
		boolean moifyFlag = false;
		for(BusinessObject bo : boList) {
			if(bo instanceof VarPageBO) {
				varPageBO = (VarPageBO) bo;
			}
			else if(bo instanceof VarPageItemBO) {
				varPageItemBO = (VarPageItemBO) bo;
				if(varPageItemBO.getId() != 0L)
					moifyFlag = true;
				varPageItemBO.setRelateName(StringUtils.removeAllSpace(varPageItemBO.getRelateName()));
			}
			else if(bo instanceof VarConfBO) {
				varConfBOList.add((VarConfBO) bo);
			}
			else if(bo instanceof SheetBO) {
				sheetBOList.add((SheetBO) bo);
			}
		}
		
		validateTabCommon(varConfBOList, sheetBOList);
		if(moifyFlag) {
			if(validateUpdate(varPageBO.getId(), varPageItemBO, varConfBOList))
				getVarPageDao().updateVarTabMapping(varPageBO, varPageItemBO, varConfBOList, sheetBOList);
		}
		else {
			if(validateInsert(varPageBO.getId(), varPageItemBO, varConfBOList))
				getVarPageDao().insertVarTabMapping(varPageBO, varPageItemBO, varConfBOList, sheetBOList);
		}
	}
	
	/**
	 * 新增变量与组件的映射关系时的校验
	 * @param varPageId
	 * @param varPageItemBO
	 * @param varConfBOList
	 * @return
	 * @throws Exception
	 */
	private boolean validateInsert(long varPageId, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList) throws Exception {
		validateVarPageItem(varPageId, varPageItemBO);
		
		return true;
	}

	/**
	 * 修改变量与组件的映射关系时的校验
	 * @param varPageId
	 * @param varPageItemBO
	 * @param varConfBOList
	 * @return
	 * @throws Exception
	 */
	private boolean validateUpdate(long varPageId, VarPageItemBO varPageItemBO, List<VarConfBO> varConfBOList) throws Exception {
		VarPageItemBO old = getVarPageDao().getVarPageItem(varPageItemBO.getId());
		if(StringUtils.isNotEqual(old.getRelateName(), varPageItemBO.getRelateName()))
			validateVarPageItem(varPageId, varPageItemBO);

		return true;
	}
	
	/**
	 * 公用校验
	 * @param varConfBOList
	 * @param varVCBOList
	 * @throws BOException
	 */
	private void validateCommon(List<VarConfBO> varConfBOList, List<VarVCBO> varVCBOList) throws BOException {
		if(ContainerUtil.isNull(varVCBOList))
			throw new BOException("组件信息不能为空！");

		for(VarConfBO varConfBO : varConfBOList) {
			if(StringUtils.isEmpty(varConfBO.getValueSetCode()))
				throw new BOException("变量值集信息不能为空！");
		}
	}
	
	/**
	 * 公用校验
	 * @param varConfBOList
	 * @param sheetBOList
	 * @throws BOException
	 */
	private void validateTabCommon(List<VarConfBO> varConfBOList, List<SheetBO> sheetBOList) throws BOException {
		if(ContainerUtil.isNull(sheetBOList))
			throw new BOException("sheet页信息不能为空！");

		for(VarConfBO varConfBO : varConfBOList) {
			if(StringUtils.isEmpty(varConfBO.getValueSetCode()))
				throw new BOException("变量值集信息不能为空！");
		}
	}
	
	/**
	 * 校验变量值-组件关联名称是否已存在
	 * @param varPageId
	 * @param varPageItemBO
	 * @throws Exception
	 * @throws BOException
	 */
	private void validateVarPageItem(long varPageId, VarPageItemBO varPageItemBO) throws Exception, BOException {
		VarPageBO varPageBO = getVarPageDao().getVarPage(varPageId);
		List<VarPageItemBO> varPageItems = varPageBO.getVarPageItems();
		if(ContainerUtil.isNull(varPageItems))
			return;
		
		for (VarPageItemBO itemBO : varPageItems) {
			if (StringUtils.isEqual(itemBO.getRelateName(), varPageItemBO.getRelateName()))
				throw new BOException("变量值-组件关联名称已存在！");
		}
	}
	
	public IVarPageDao getVarPageDao() {
		return varPageDao;
	}

	public void setVarPageDao(IVarPageDao varPageDao) {
		this.varPageDao = varPageDao;
	}
}
