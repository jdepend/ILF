package com.qeweb.framework.pl.android;

import java.util.Comparator;

import com.qeweb.framework.bc.sysbo.TreeBO;

public class NodeComparator implements Comparator<TreeBO> {

    @Override
    public int compare(TreeBO obj1, TreeBO obj2) {
        int flag = 0;
        if(obj1 == null || obj2 == null)
            return flag;
        flag = Long.valueOf(obj1.getParentId()).compareTo(Long.valueOf(obj2.getParentId()));
        if(flag == 0)
            flag = Long.valueOf(obj1.getSortIndex()).compareTo(Long.valueOf(obj2.getSortIndex()));
        return flag;
    }
}
