package com.liangxiaolin.rdcchat.RDCChat1.service;

import com.liangxiaolin.rdcchat.RDCChat1.entity.Moment;
import com.liangxiaolin.rdcchat.RDCChat1.entity.MomentsDetail;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Page;

public interface MomentsService {

    boolean issueMoment(Moment moment);

    Page<MomentsDetail> getMoments(int currentPage, int userId);

    boolean cancelLikeIt(int userId, String momentId);

    boolean likeIt(int userId, String momentId);

    boolean postComment(int userId, String momentId, String content);
}
