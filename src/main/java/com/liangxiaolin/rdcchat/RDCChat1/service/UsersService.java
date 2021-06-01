package com.liangxiaolin.rdcchat.RDCChat1.service;


import com.liangxiaolin.rdcchat.RDCChat1.entity.Manager;
import com.liangxiaolin.rdcchat.RDCChat1.entity.ReportMessage;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Users;

import java.util.List;

public interface UsersService {
    /**
     * 注册 把用户信息存入数据库
     * @param user
     * @return
     */
    boolean regist(Users user) throws Exception;

    /**
     * 查看用户名是否唯一
     * @param userName
     * @return
     */
    boolean findByUserName(String userName);

    boolean findByEmail(String email);

    boolean findByTelephone(String telephone);

    boolean findByIdNumber(String idNumber);

    boolean saveImg(Users user);

    boolean active(String code);

    Users logIn(Users user) throws Exception;

    boolean updateUserPassword(String email, String userPassword) throws Exception;

    boolean updateUserMessage(Users user);

    Manager managerLogIn(Manager manager);

    boolean blockFriend(String userId);

    List<ReportMessage> searchReportMessage();
}
