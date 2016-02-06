package com.qeweb.framework.impconfig.promember.bo;

import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.ImpConfigConstant;
import com.qeweb.framework.impconfig.common.util.pathutil.ImpUsersPathUtil;
import com.qeweb.framework.impconfig.promember.bop.ProjectMemberRoleBOP;
import com.qeweb.framework.impconfig.promember.dao.ia.IProjectMemberDao;

/**
 * 项目成员管理，对应qeweb-impuser.xml.
 * <p>
 * 用于配置角色, 仅项目经理有权限修改. 角色分为项目经理(pm), 开发人员(dev), 实施人员(imp).
 * 三者均属于开发期角色, 游离于运行期的权限管理.
 * 拥有这三个角色的用户在登陆时应当没有图形码校验, 在页面上方的logo域显示"开发板"字样, 并显示当前SVN版本号.
 * 当项目中有 qeweb-impuser.xml文件, 并且用 qeweb-impuser.xml中配置的用户登陆后,
 * qeweb-impuser.xml中的菜单会取代数据库中的菜单(运行期菜单).
 *
 */
public class ProjectMemberBO extends BusinessObject {

	private static final long serialVersionUID = -8423287274277731223L;
	private String memberCode;			//成员编码
	private String memberName;			//成员名称
	private String password;			//密码
	private String newPassword;			//新密码
	private String newPasswordAgain;	//确认密码
	private String memberRole;			//项目成员角色
	private String memberRoleName;		//项目成员角色名
	private String remark;				//备注
	
	private IProjectMemberDao projectMemberDao;
	
	public ProjectMemberBO(){
		super();
		addBOP("memberCode", new NotEmptyBop(1, 20));
		addBOP("memberName", new NotEmptyBop(1, 20));
		addBOP("password", new NotEmptyBop(1, 50));
		addBOP("newPassword", new NotEmptyBop(1, 50));
		addBOP("newPasswordAgain", new NotEmptyBop(1, 50));
		addBOP("memberRole", new ProjectMemberRoleBOP());
		addBOP("remark", new EmptyBop(200));
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		Page page = null;
		List<ProjectMemberBO> members = getProjectMemberDao().findMembers(bot);
		page = new Page(members, members.size(), members.size(), 0);
		initPreferencePage(page);
		
		return page;
	}
	
	/**
	 * 验证口令是否正确
	 * @param memberCode
	 * @param password
	 * @return
	 */
	public ProjectMemberBO getProjectMember(String memberCode, String password) {
		//服务器端的eweb-preference.xml是否存在
		if(!FileUtil.isFile(ImpUsersPathUtil.getServerImpuserPath()))
			return null;
			
		return getProjectMemberDao().getProjectMember(memberCode, password);
	}
	
	/**
	 * 跳转到修改密码页面
	 * @return
	 * @throws Exception
	 */
	public ProjectMemberBO toModifyPwd() throws Exception {
		ProjectMemberBO member = getRecord(UserContext.getProjectMemberBO().getId());
		member.setPassword("");
		BOHelper.initPreferencePage(member);
		return member;
	}
	
	/**
	 * 重置密码
	 * @param bo
	 * @throws Exception 
	 */
	public void resetPwd(ProjectMemberBO bo) throws Exception {
		ProjectMemberBO member = getRecord(bo.getId()); 
		member.setPassword(ImpConfigConstant.DEFAULT_MEMBER_PASSWORD);
		getProjectMemberDao().update(member);
	}
	
	/**
	 * 修改密码
	 * @param bo
	 * @throws Exception 
	 */
	public void modifyPassword(ProjectMemberBO bo) throws Exception{
		ProjectMemberBO oldMember = getRecord(bo.getId()); 
		if(StringUtils.isNotEqual(bo.getPassword(), oldMember.getPassword())) {
			throw new BOException("旧密码错误！");
		}
		else if(StringUtils.isNotEqual(bo.getNewPassword(), bo.getNewPasswordAgain())) {
			throw new BOException("新密码和确认密码不一致！");
		}
		else {
			oldMember.setPassword(bo.getPassword());
			getProjectMemberDao().update(oldMember);
		}
	}
	
	@Override
	public ProjectMemberBO getRecord(long id) throws Exception {
		ProjectMemberBO bo = getProjectMemberDao().getPorjectMember(id);
		if(bo == null)
			return null;
		
		BOHelper.initPreferencePage(bo);
		return bo;
	}

	/**
	 * 保存数据
	 * @param menber
	 * @throws BOException
	 */
	public void insert(ProjectMemberBO menber) throws Exception {
		if(validateInsert(menber)) {
			menber.setId(GuidUtil.getGUID());
			menber.setPassword(ImpConfigConstant.DEFAULT_MEMBER_PASSWORD);
			menber.setDeleteFlag(IBaseDao.UNDELETE_SIGNE);
			
			getProjectMemberDao().insert(menber);
		}
	}
	
	/**
	 * 修改数据
	 * @param bo
	 * @throws Exception 
	 */
	public void update(ProjectMemberBO bo) throws Exception {
		ProjectMemberBO oldMember = getRecord(bo.getId());
		if(validateUpdate(oldMember, bo)) {
			oldMember.setMemberName(bo.getMemberName());
			oldMember.setMemberCode(bo.getMemberCode());
			oldMember.setMemberRole(bo.getMemberRole());
			oldMember.setRemark(bo.getRemark());
			
			getProjectMemberDao().update(oldMember);
		}
	}
	
	/**
	 * 删除成员
	 * @param members
	 */
	@Override
	public void delete(List<BusinessComponent> members){
		getProjectMemberDao().delete(members);
	}
	
	/**
	 * insert前的校验操作
	 * @param member
	 * @return
	 * @throws BOException 
	 */
	private boolean validateInsert(ProjectMemberBO member) throws Exception {
		if(getProjectMemberDao().getPorjectMemberByCode(member.getMemberCode()) != null) 
			throw new BOException("项目成员编码不能重复！");
		else if(getProjectMemberDao().getPorjectMemberByName(member.getMemberName()) != null)
			throw new BOException("项目成员名称不能重复！");
		else 
			return true;
	}
	
	/**
	 * update前的校验操作
	 * @param member
	 * @return
	 * @throws BOException 
	 */
	private boolean validateUpdate(ProjectMemberBO oldMember, ProjectMemberBO newMember) throws Exception {
		//修改了编码
		if(!StringUtils.isEqual(oldMember.getMemberCode(), newMember.getMemberCode())) {
			if(getProjectMemberDao().getPorjectMemberByCode(newMember.getMemberCode()) != null) 
				throw new BOException("项目成员编码不能重复！");
		}
		//修改了名称
		if(!StringUtils.isEqual(oldMember.getMemberName(), newMember.getMemberName())) {
			if(getProjectMemberDao().getPorjectMemberByName(newMember.getMemberName()) != null) 
				throw new BOException("项目成员名称不能重复！");
		}
		
		return true;
	}

	/**
	 * 是否是项目经理
	 * @return
	 */
	public boolean isPM() {
		return StringUtils.hasSubString(getMemberRole(), ProjectMemberRoleBOP.PM);
	}
	
	/**
	 * 是否是开发人员
	 * @return
	 */
	public boolean isDEV() {
		return StringUtils.hasSubString(getMemberRole(), ProjectMemberRoleBOP.DEV);
	}
	
	/**
	 * 是否是实施人员
	 * @return
	 */
	public boolean isIMP() {
		return StringUtils.hasSubString(getMemberRole(), ProjectMemberRoleBOP.IMP);
	}
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}

	public String getMemberRoleName() {
		return memberRoleName;
	}

	public void setMemberRoleName(String memberRoleName) {
		this.memberRoleName = memberRoleName;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IProjectMemberDao getProjectMemberDao() {
		if(projectMemberDao == null)
			projectMemberDao = (IProjectMemberDao)SpringConstant.getCTX().getBean("projectMemberDao");
		return projectMemberDao;
	}

	public void setProjectMemberDao(IProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}
	
}
