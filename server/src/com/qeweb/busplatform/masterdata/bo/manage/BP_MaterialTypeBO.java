package com.qeweb.busplatform.masterdata.bo.manage;

import java.util.ArrayList;
import java.util.List;

import com.qeweb.busplatform.masterdata.bop.ImportantDegreeEnumBOP;
import com.qeweb.busplatform.masterdata.dao.ia.IBP_MaterialTypeDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 品类管理
 */
public class BP_MaterialTypeBO extends CheckTreeBO {

	private static final long serialVersionUID = 8257886371717044883L;

	private String typeName;                    //品类名称
    private String typeCode;                    //品类编码
    private String importantDegree;             //重要度
    private String remarks;                     //备注
    private String description;                 //明细
    private Long parentTypeId;					//
    private String parentTypeName;				//

	// 扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	
	private IBP_MaterialTypeDao materialTypeDao; 

	public BP_MaterialTypeBO() {
		super();
        addBOP("typeName", new NotEmptyBop());
        addBOP("importantDegree", new NotEmptyBopDec(new ImportantDegreeEnumBOP()));
        addBOP("remarks", new EmptyBop(150));
        addOperateBOP("delete", new SelectMdBOP());
	}

	public BP_MaterialTypeBO(long id, long parentId, String value,
			int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		List<BP_MaterialTypeBO> list = getDao().findAll(BP_MaterialTypeBO.class);

		for (BP_MaterialTypeBO materialTypeBO : list) {
			BP_MaterialTypeBO menu = new BP_MaterialTypeBO(materialTypeBO.getId(), materialTypeBO.getParentTypeId(),
					materialTypeBO.getTypeName(), materialTypeBO.getSortIndex(), false);
			this.add(menu);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void createCheckTree(String materialTypeIds) {
		List<BP_MaterialTypeBO> list = getDao().findAll(BP_MaterialTypeBO.class);
		
		String[] types = materialTypeIds.split(",");
		List<Long> typeList = new  ArrayList<Long>();
		for (String id : types) {
			typeList.add(Long.valueOf(id));
		}
		for (BP_MaterialTypeBO materialTypeBO : list) {
			BP_MaterialTypeBO menu = new BP_MaterialTypeBO(materialTypeBO.getId(), materialTypeBO.getParentTypeId(),
					materialTypeBO.getTypeName(), materialTypeBO.getSortIndex(), typeList.contains(materialTypeBO.getId()) ? true : false);
			
			this.add(menu);
		}
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
        if (bot == null || bot.getBotMap().containsKey("materialType.typeName")
                || bot.getBotMap().containsKey("materialType.id")) {
            bot = new BOTemplate();
        }
        if(bot.getBoName() != null && bot.getBoName().equalsIgnoreCase("bpVendorBaseInfoBO") && bot.getBotMap().containsKey("materialTypeIds")) {
        	createCheckTree((String) bot.getBotMap().get("bot.getBotMap()"));
        }
        else if(bot.getBoName() != null && bot.getBoName().equalsIgnoreCase("bpVendorBaseInfoBO")  && !bot.getBotMap().containsKey("materialTypeIds")){
        	return null;
        }
        return super.query(bot, start);
	}

	public void insert() throws Exception {
		generateMaterialTypeCode();
		super.insert();
	}

	@SuppressWarnings("rawtypes")
	private void generateMaterialTypeCode() {
		String strCode = "100";
        Long parentTypeId = this.getParentTypeId();
        if (parentTypeId == null) {
            List results = materialTypeDao.getMaterialTypesWithParentTypeIdNull();
            if (results != null && results.size() > 0) {
                Object[] result = (Object[]) results.get(0);
                long nextCode = Long.valueOf(String.valueOf(result[1])) + 1;
                strCode = nextCode + "";
            }
            this.setTypeCode(strCode);
            return;
        }
        List list = materialTypeDao.getMaterialTypesByParentTypeId(parentTypeId); 
        if (list != null && list.size() > 0) {
            Object[] objs = (Object[]) list.get(0);
            long maxCode = Long.valueOf(String.valueOf(objs[1])) + 1;
            this.setTypeCode(String.valueOf(maxCode));
        } else {
            BP_MaterialTypeBO parentMaterialType = (BP_MaterialTypeBO) getDao()
                    .getById(this.getSearchClass(),
                    parentTypeId);
            this.setTypeCode(parentMaterialType.getTypeCode() + strCode);
        }
	}

	@Override
	public long getRootId() {
		return 0L;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getImportantDegree() {
		return importantDegree;
	}

	public void setImportantDegree(String importantDegree) {
		this.importantDegree = importantDegree;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParentTypeId() {
		if(parentTypeId == null)
			return 0l;
		return parentTypeId;
	}

	public void setParentTypeId(Long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}

	public void setMaterialTypeDao(IBP_MaterialTypeDao materialTypeDao) {
		this.materialTypeDao = materialTypeDao;
	}

	public IBP_MaterialTypeDao getMaterialTypeDao() {
		return materialTypeDao;
	}
}
