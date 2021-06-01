package com.liangxiaolin.rdcchat.RDCChat1.dao;


import com.liangxiaolin.rdcchat.RDCChat1.entity.Manager;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Reporting;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Users;

import java.util.List;

public interface UsersDao {
    boolean regist(Users user);

    boolean findByUserName(String userName);

    boolean findByEmail(String email);

    boolean findByTelephone(String telephone);

    boolean findByIdNumber(String idNumber);

    boolean saveImg(Users user,List<String> values);

    Users findByCode(String code);

    boolean updateStatus(Users user);

    Users logIn(Users user, List<String> values);

    boolean updateUserPassword(Users user, List<String> setList);

    boolean updateUserMessage(Users user, List<String> setList);

    Manager managerLogIn(Manager manager,List<String> values);

    boolean blockFriend(Users users, List<String> values);

    List<Reporting> searchReportMessage(Reporting reporting);

    Users findA(int accuserId);

}
