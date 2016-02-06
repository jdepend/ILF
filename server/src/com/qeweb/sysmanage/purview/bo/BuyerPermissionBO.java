package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * 采购商权限BO, 用于分配采购商权限.
 * <br>
 * BuyerPermissionBO继承CheckTreeBO,将所有采购商展示为一个树形结构,并自动勾选已经存在的采购商权限.
 */
public class BuyerPermissionBO extends CheckTreeBO {
	private static final long serialVersionUID = -840147785758468270L;

	public BuyerPermissionBO() {
		super();
	}
	
	public BuyerPermissionBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	/**
	 * 构造采购商权限checkTree
	 * @return
	 * @throws Exception 
	 */
	public BuyerPermissionBO cerateBuyerTree(RoleBO roleBO) throws Exception {
		create();
		Set<OrganizationBO> buyers = null;
		RoleBO temp = (RoleBO)roleBO.getRecord(roleBO.getId());
		if(temp != null)
			buyers = temp.getBuyers();
		
		if(ContainerUtil.isNull(buyers))
			return this;
		
		List<Long> orgIds = new LinkedList<Long>();
		for(OrganizationBO org : buyers) {
			orgIds.add(org.getId());
		}
		
		//将已有供应商角色标记为checked
		for(TreeBO treeBO : this.getTree()) {
			BuyerPermissionBO bo = (BuyerPermissionBO) treeBO;
			if(orgIds.indexOf(bo.getId()) != -1 )
				bo.setChecked(true);
		}
		
		return this;
	}
	
    /**
     * 根节点
     */
    @Override
    public long getRootId() {
        return 0L;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void create() {
    	DetachedCriteria criteria = DetachedCriteria.forClass(OrganizationBO.class);
    	criteria.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
    	criteria.add(Restrictions.eq("orgType", OrgTypeBOP.TYPE_BUYER));
    	criteria.addOrder(Order.asc("orgName"));
    	List<OrganizationBO> orgList = getDao().findByCriteria(criteria);
    	
    	if(ContainerUtil.isNull(orgList))
    		return;
    	
        int firstNodeId = -1;
        BuyerPermissionBO firstNode = new BuyerPermissionBO(firstNodeId, getRootId(), AppLocalization.getLocalization("checkAll"), 0, false);
        this.add(firstNode);
        
        for (OrganizationBO org : orgList) {
        	BuyerPermissionBO node = 
        		new BuyerPermissionBO(org.getId(), firstNodeId, 
        				org.getOrgName() + "【" + org.getOrgCode() + "】", 
        				0, false);
            this.add(node);
        }
    }
}
