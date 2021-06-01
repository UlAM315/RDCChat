package com.liangxiaolin.rdcchat.RDCChat1.dao;

import com.liangxiaolin.rdcchat.RDCChat1.entity.Comments;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Moment;
import com.liangxiaolin.rdcchat.RDCChat1.entity.MomentLike;

import java.util.List;

public interface MomentsDao {
    boolean issueMoment(Moment moment);

    List<Moment> getMomentsByFriendsId(int friendId);

    List<String> getCommentsByMomentId(int momentId);

    boolean findIfLikeMoment(int momentId, int userId);

    boolean likeIt(MomentLike momentLike);

    boolean cancelLikeIt(MomentLike momentLike, List<String> values);

    boolean subLikeNumber(int momentId1);

    boolean addLikeNumber(int momentId1);

    boolean postComment(Comments comments);
}
