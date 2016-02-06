package com.rofine.platform.model.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2015/12/4.
 */
@Repository
public class DeptUserInfoDao {

    private List<DeptUserInfo> deptInfos;

    public DeptUserInfoDao() {

        DeptUserInfo deptInfo;

        UserInfo userInfo;

        deptInfos = new ArrayList<DeptUserInfo>();

        deptInfo = new DeptUserInfo();
        deptInfo.setName("办公室");

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar");
        userInfo.setName("张明明");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("良好");
        userInfo.setStateCode(4);

        deptInfo.addUserInfo(userInfo);

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar1");
        userInfo.setName("丽丽");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("一般");
        userInfo.setStateCode(3);

        deptInfo.addUserInfo(userInfo);

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar2");
        userInfo.setName("与飞");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("优秀");
        userInfo.setStateCode(5);

        deptInfo.addUserInfo(userInfo);

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar3");
        userInfo.setName("宁宇");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("优秀");
        userInfo.setStateCode(5);

        deptInfo.addUserInfo(userInfo);

        deptInfos.add(deptInfo);

        deptInfo = new DeptUserInfo();
        deptInfo.setName("人事科");

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar4");
        userInfo.setName("佳佳");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("一般");
        userInfo.setStateCode(3);

        deptInfo.addUserInfo(userInfo);


        userInfo = new UserInfo();
        userInfo.setAvatar("avatar5");
        userInfo.setName("旺旺");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("一般");
        userInfo.setStateCode(3);

        deptInfo.addUserInfo(userInfo);

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar6");
        userInfo.setName("刘兵");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("优秀");
        userInfo.setStateCode(5);

        deptInfo.addUserInfo(userInfo);

        deptInfos.add(deptInfo);

        deptInfo = new DeptUserInfo();
        deptInfo.setName("稽查处");

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar7");
        userInfo.setName("张飞");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("一般");
        userInfo.setStateCode(3);

        deptInfo.addUserInfo(userInfo);


        userInfo = new UserInfo();
        userInfo.setAvatar("avatar8");
        userInfo.setName("韩信");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("一般");
        userInfo.setStateCode(3);

        deptInfo.addUserInfo(userInfo);

        userInfo = new UserInfo();
        userInfo.setAvatar("avatar9");
        userInfo.setName("周瑜");
        userInfo.setSex("男");
        userInfo.setDuty("科长");
        userInfo.setState("优秀");
        userInfo.setStateCode(5);

        deptInfo.addUserInfo(userInfo);

        deptInfos.add(deptInfo);
    }

    public List<DeptUserInfo> getDeptInfos() {
        return deptInfos;
    }

    public UserInfo getUserInfo(String userId) {
        for (DeptUserInfo deptUserInfo : this.deptInfos) {
            for (UserInfo userInfo : deptUserInfo.getUserInfos()) {
                if (userInfo.getId().equals(userId)) {
                    return userInfo;
                }
            }
        }
        return null;
    }
}
