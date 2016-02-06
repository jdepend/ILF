package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * 用户tree BO, 用于分配用户组中的用户.
 * <p>
 * UserTreeBO继承CheckTreeBO,将所有用户展示为一个树形结构,并自动勾选用户组中已经存在的用户.
 * </p>
 */
public class UserTreeBO extends CheckTreeBO {

	private static final long serialVersionUID = 1453172397573575363L;
	final static public int firstNodeId = -1;
	
	public UserTreeBO() {
		super();
	}
	
	public UserTreeBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	/**
	 * 构造用户组下的用户checkTree
	 * @param userGroupBO
	 * @return
	 * @throws Exception 
	 */
	public UserTreeBO createUserTree(UserGroupBO userGroupBO) throws Exception {
		create();
		Set<UserBO> users = null;
		UserGroupBO temp = (UserGroupBO)userGroupBO.getRecord(userGroupBO.getId());
		if(temp != null)
			users = temp.getUsers();
		
		if(ContainerUtil.isNull(users))
			return this;
		
		List<Long> userIds = new LinkedList<Long>();
		for(UserBO user : users) {
			userIds.add(user.getId());
		}
		
		//将已有用户标记为checked
		for(TreeBO treeBO : this.getTree()) {
			UserTreeBO bo = (UserTreeBO) treeBO;
			if(userIds.contains(bo.getId()))
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
    	BOTemplate bot = new BOTemplate();
    	bot.push("organizationBO.orgType", OrgTypeBOP.TYPE_BUYER);
    	bot.push(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE);
    	DetachedCriteria criteria = BOTHelper.getDetachedCriteria(bot, UserBO.class);
    	criteria.addOrder(Order.asc("organizationBO.orgCode"));
    	List<UserBO> userList = getDao().findByCriteria(criteria);
    	
    	if(ContainerUtil.isNull(userList))
    		return;
    	
        UserTreeBO firstNode = new UserTreeBO(firstNodeId, getRootId(), AppLocalization.getLocalization("checkAll"), 0, false);
        this.add(firstNode);
        
        for (UserBO user : userList) {
        	UserTreeBO node = 
        		new UserTreeBO(user.getId(), firstNodeId, 
        				user.getUserCode() + " | " + user.getUserName() + "【" + user.getOrganizationBO().getOrgName() + "】", 0, false);
            this.add(node);
        }
    }
}
