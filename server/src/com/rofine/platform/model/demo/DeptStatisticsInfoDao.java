package com.rofine.platform.model.demo;

import com.rofine.platform.web.model.data.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2015/12/15.
 */
@Repository
public class DeptStatisticsInfoDao {

    private List<DeptStatisticsInfo> deptInfos;

    public DeptStatisticsInfoDao(){

        DeptStatisticsInfo deptInfo;

        deptInfos = new ArrayList<DeptStatisticsInfo>();

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("办公室");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("人事科");
        deptInfo.setUserCount(3);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("稽查室");
        deptInfo.setUserCount(6);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("管理办公室");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("征管处");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("资源部");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("总裁办");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("秘书处");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("人力资源部");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("监察部");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("华为");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("小米");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);

        deptInfo = new DeptStatisticsInfo();
        deptInfo.setName("百度");
        deptInfo.setUserCount(5);
        deptInfo.setWorkCount(60);

        deptInfos.add(deptInfo);
    }

    public List<DeptStatisticsInfo> getDeptList(Page page) {

        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();

        List<DeptStatisticsInfo> rtnDeptInfos = new ArrayList();

        for(int index = 0; index < deptInfos.size(); index++){
            if(index >= pageNumber * pageSize && index < pageNumber * pageSize + pageSize){
                rtnDeptInfos.add(this.deptInfos.get(index));
            }
        }

        page.setMore((pageNumber + 1) * pageSize < deptInfos.size());

        return rtnDeptInfos;
    }

    public List<DeptStatisticsInfo> getDeptInfos() {
        return deptInfos;
    }
}
