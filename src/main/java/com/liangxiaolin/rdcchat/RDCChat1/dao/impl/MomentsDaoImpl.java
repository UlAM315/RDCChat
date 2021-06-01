package com.liangxiaolin.rdcchat.RDCChat1.dao.impl;

import com.liangxiaolin.rdcchat.RDCChat1.dao.MomentsDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectCRUD;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Comments;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Moment;
import com.liangxiaolin.rdcchat.RDCChat1.entity.MomentLike;

import java.util.ArrayList;
import java.util.List;

public class MomentsDaoImpl implements MomentsDao {

    @Override
    public boolean issueMoment(Moment moment) {
        return ReflectCRUD.add(moment);
    }

    @Override
    public List<Moment> getMomentsByFriendsId(int friendId) {
        Moment moment = new Moment();
        moment.setUserId(friendId);
        List<Moment> momentList = ReflectCRUD.queryList(moment,"userId");
        System.out.println("friendId:"+ friendId + "momentList:" + momentList);
        return momentList;
    }

    @Override
    public List<String> getCommentsByMomentId(int momentId) {
        List<String> commentStringList = new ArrayList<>();
        Comments comments = new Comments();
        comments.setMomentId(momentId);
        List<Comments> commentsList = ReflectCRUD.queryList(comments,"momentId");
        System.out.println("commentsList:"+commentsList);
        if (commentsList!=null && !commentsList.isEmpty()){
            for (Comments comments1 : commentsList) {
                //获取所有评论内容
                commentStringList.add(comments1.getContent());
            }
            System.out.println("commentStringList:"+commentStringList);
        }

        return commentStringList;
    }

    @Override
    public boolean findIfLikeMoment(int momentId, int userId) {
        MomentLike momentLike = new MomentLike(momentId,userId);
        List<String> values = new ArrayList<>();
        values.add("momentId");
        values.add("userId");
        if (ReflectCRUD.queryObjectAnd(momentLike,values)!=null){
            //确实已经点赞了
            return true;
        }
        return false;
    }


    @Override
    public boolean likeIt(MomentLike momentLike) {

        return ReflectCRUD.add(momentLike);
    }

    @Override
    public boolean cancelLikeIt(MomentLike momentLike, List<String> values) {
        return ReflectCRUD.delete(momentLike,values);
    }

    @Override
    public boolean subLikeNumber(int momentId1) {
        Moment moment= new Moment();
        moment.setMomentId(momentId1);
        int likeNumber = ReflectCRUD.queryObject(moment,"momentId").getLikeNumber();
        moment.setLikeNumber(likeNumber-1);
        List<String> values = new ArrayList<>();
        values.add("likeNumber");
        return ReflectCRUD.change(moment,values,"momentId",moment);
    }

    @Override
    public boolean addLikeNumber(int momentId1) {
        Moment moment= new Moment();
        moment.setMomentId(momentId1);
        int likeNumber = ReflectCRUD.queryObject(moment,"momentId").getLikeNumber();
        moment.setLikeNumber(likeNumber+1);
        List<String> values = new ArrayList<>();
        values.add("likeNumber");
        return ReflectCRUD.change(moment,values,"momentId",moment);
    }

    @Override
    public boolean postComment(Comments comments) {
        return ReflectCRUD.add(comments);
    }
}
