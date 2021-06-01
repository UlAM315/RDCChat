package com.liangxiaolin.rdcchat.RDCChat1.service.impl;

import com.liangxiaolin.rdcchat.RDCChat1.dao.MomentsDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.impl.MomentsDaoImpl;
import com.liangxiaolin.rdcchat.RDCChat1.entity.*;
import com.liangxiaolin.rdcchat.RDCChat1.service.MomentsService;
import com.liangxiaolin.rdcchat.RDCChat1.service.MyFriendsService;

import java.util.ArrayList;
import java.util.List;

public class MomentsServiceImpl implements MomentsService {

    MomentsDao dao = new MomentsDaoImpl();
    MyFriendsService myFriendsService = new MyFriendsServiceImpl();

    @Override
    public boolean issueMoment(Moment moment) {
        moment.setLikeNumber(0);
        return dao.issueMoment(moment);
    }

    @Override
    public Page<MomentsDetail> getMoments(int currentPage, int userId) {
        Page<MomentsDetail> page = new Page<>();
        //设置当前页码
        page.setCurrentPage(currentPage);

        //用来记录朋友圈总数
        int i = 0;

        //创建momengtsdetail集合
        List<MomentsDetail> momentsDetailList = new ArrayList<>();
        //设置当前页显示的数据集合
        List<FriendAndUsers> friendAndUsers = myFriendsService.getfriendsListsByPage(userId,currentPage);
        if (friendAndUsers != null && !friendAndUsers.isEmpty()){
            //有好友说明就有朋友圈说明有momentdetail
            List<Moment> momentList = new ArrayList<>();
            for (FriendAndUsers friendAndUser : friendAndUsers) {
                int friendId = friendAndUser.getFriendId();

                //根据friendId找到他们本人的朋友圈s
                List<Moment> moments = dao.getMomentsByFriendsId(friendId);
                //获取了本好友的所有moment
                if (moments!=null && !moments.isEmpty()){
                    for (Moment moment : moments) {
                        System.out.println("moment:"+moment);
                        //从而获得本页好友的所有moment进而获得其评论
                        momentList.add(moment);

                        //设置这个朋友的每个momentsDetail---------------------------------
                        MomentsDetail momentsDetail = new MomentsDetail();
                        momentsDetail.setUserId(friendAndUser.getFriendId());
                        String nickName = friendAndUser.getNickname();
                        if (nickName == null){
                            momentsDetail.setUserName(friendAndUser.getUserName());
                        }else {
                            momentsDetail.setUserName(nickName);
                        }
                        int momentId = moment.getMomentId();
                        momentsDetail.setMomentId(momentId);
                        momentsDetail.setAvatarImg(friendAndUser.getAvatarImg());
                        momentsDetail.setContent(moment.getContent());
                        String firstImg = moment.getFirstImg();
                        if(firstImg!=null){
                            momentsDetail.setFirstImg(firstImg);
                        }
                        String secondImg = moment.getSecondImg();
                        if(secondImg!=null){
                            momentsDetail.setSecondImg(secondImg);
                        }
                        String thirdImg = moment.getThirdImg();
                        if (thirdImg!=null){
                            momentsDetail.setThirdImg(thirdImg);
                        }
                        momentsDetail.setIssueTime(moment.getIssueTime());
                        momentsDetail.setLikeNumber(moment.getLikeNumber());
                        //判断是否点赞了这朋友圈
                        if (dao.findIfLikeMoment(momentId,userId)){
                            momentsDetail.setIfLike(true);
                        }else {
                            momentsDetail.setIfLike(false);
                        }
                        //从而获得这个好友的所有朋友圈detail
                        //进而获得本页好友的所有momentsdetail
                        momentsDetailList.add(momentsDetail);
                        //--------------------------------------------------
                    }
                }

            }
            if(momentList!=null && !momentList.isEmpty()){
                //根据momentId找到所有评论
                for (Moment moment : momentList) {
                    for (MomentsDetail momentsDetail : momentsDetailList) {
                        if (momentsDetail.getMomentId() == moment.getMomentId()){
                            int momentId = moment.getMomentId();
                            List<String> comments = dao.getCommentsByMomentId(momentId);
                            //设置这个moment的评论
                            momentsDetail.setComment(comments);
                        }
                    }
                    //记录一共有多少条朋友圈，一个momentdetail代表一条朋友圈
                    i++;
                }
            }
        }
        //设置总记录数
        int totalCount = i;
        page.setTotalCount(totalCount);
        page.setList(momentsDetailList);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % 10 == 0 ? totalCount / 10 :(totalCount / 10) + 1 ;
        page.setTotalPage(totalPage);


        return page;
    }

    @Override
    public boolean cancelLikeIt(int userId, String momentId) {
        //点赞表中删除数据
        int momentId1 = Integer.parseInt(momentId);
        MomentLike momentLike = new MomentLike(momentId1,userId);
        List<String> values = new ArrayList<>();
        values.add("momentId");
        values.add("userId");
        //点赞数-1
        return (dao.cancelLikeIt(momentLike,values)&&dao.subLikeNumber(momentId1));
    }

    @Override
    public boolean likeIt(int userId, String momentId) {
        int momentId1 = Integer.parseInt(momentId);
        MomentLike momentLike = new MomentLike(momentId1,userId);
        //点赞数+1
        return (dao.likeIt(momentLike)&&dao.addLikeNumber(momentId1));
    }

    @Override
    public boolean postComment(int userId, String momentId, String content) {
        int momentId1 = Integer.parseInt(momentId);
        Comments comments = new Comments(momentId1,content,userId);
        return dao.postComment(comments);
    }
}
