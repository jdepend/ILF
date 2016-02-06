package com.rofine.platform.model.demo;

import com.rofine.platform.web.model.data.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2015/12/17.
 */
@Repository
public class MessageDao {

    private List<NoticeInfo> noticeInfos;

    public MessageDao(){

        NoticeInfo noticeInfo;

        noticeInfos = new ArrayList<NoticeInfo>();

        noticeInfo = new NoticeInfo();
        noticeInfo.setTitle("'我的中国梦'系列学习活动");
        noticeInfo.setContent("学的不错");

        noticeInfos.add(noticeInfo);

        noticeInfo = new NoticeInfo();
        noticeInfo.setTitle("28日前查看工作任务列表");
        noticeInfo.setContent("学的不错");

        noticeInfos.add(noticeInfo);

        noticeInfo = new NoticeInfo();
        noticeInfo.setTitle("上半年绩效考核成绩公布");
        noticeInfo.setContent("学的不错");

        noticeInfos.add(noticeInfo);
    }

    public List<NoticeInfo> getNotices(Page page){
        return noticeInfos;
    }

    public List<LikeInfo> getLikes(Page page){
        return new ArrayList<LikeInfo>();
    }

    public List<LikeInfo> getFollows(Page page){
        return new ArrayList<LikeInfo>();
    }
}
