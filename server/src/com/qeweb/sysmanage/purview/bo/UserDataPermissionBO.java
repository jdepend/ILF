package com.qeweb.sysmanage.purview.bo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * 用户数据权限BO, 用于分配角色拥有哪些用户的数据权限.
 * <br>
 * UserDataPermissionBO继承CheckTreeBO,将所有用户展示为一个树形结构,并自动勾选已经存在的用户数据权限.
 */
public class UserDataPermissionBO extends CheckTreeBO {

	private static final long serialVersionUID = 1453172397573575363L;

	public UserDataPermissionBO() {
		super();
	}
	
	public UserDataPermissionBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	/**
	 * 构造用户数据权限checkTree
	 * @param roleBO
	 * @return
	 * @throws Exception 
	 */
	public UserDataPermissionBO createUserTree(RoleBO roleBO) throws Exception {
		create();
		Set<UserBO> users = null;
		RoleBO temp = (RoleBO)roleBO.getRecord(roleBO.getId());
		if(temp != null)
			users = temp.getUsers();
		
		if(ContainerUtil.isNull(users))
			return this;
		
		List<Long> orgIds = new LinkedList<Long>();
		for(UserBO user : users) {
			orgIds.add(user.getId());
		}
		
		//将已有用户标记为checked
		for(TreeBO treeBO : this.getTree()) {
			UserDataPermissionBO bo = (UserDataPermissionBO) treeBO;
			if(orgIds.contains(bo.getId()))
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
    	List<UserBO> userList = getDao().findByCriteria(criteria);
    	
    	if(ContainerUtil.isNull(userList))
    		return;
    	
        int firstNodeId = -1;
        UserDataPermissionBO firstNode = new UserDataPermissionBO(firstNodeId, getRootId(), AppLocalization.getLocalization("checkAll"), 0, false);
        this.add(firstNode);
        
        for (UserBO user : userList) {
        	UserDataPermissionBO node = 
        		new UserDataPermissionBO(user.getId(), firstNodeId, 
        				user.getUserName() + "【" + user.getOrganizationBO().getOrgName() + "】", 
        				0, false);
            this.add(node);
        }
    }
}
