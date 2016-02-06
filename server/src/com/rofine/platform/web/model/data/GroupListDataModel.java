package com.rofine.platform.web.model.data;

import java.util.List;

/**
 * Created by kingbox on 2016/2/3.
 */
public class GroupListDataModel extends ListDataModel {

    private List groupListData;

    private int subListIndex;

    public List getGroupListData() {
        return groupListData;
    }

    public void setGroupListData(List groupListData) {
        this.groupListData = groupListData;
    }

    public int getSubListIndex() {
        return subListIndex;
    }

    public void setSubListIndex(int subListIndex) {
        this.subListIndex = subListIndex;
    }
}
