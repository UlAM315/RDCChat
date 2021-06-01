package com.liangxiaolin.rdcchat.RDCChat1.dao.impl;


import com.liangxiaolin.rdcchat.RDCChat1.dao.UsersDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectCRUD;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Manager;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Reporting;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersDaoImpl implements UsersDao {

    ReflectCRUD crud = new ReflectCRUD();

    @Override
    public boolean regist(Users user) {
        return ReflectCRUD.add(user);
    }

    @Override
    public boolean findByUserName(String userName) {
        boolean flag = false;
        Users user = new Users();
        user.setUserName(userName);
        if(crud.queryObject(user,"userName") != null){
            //存在该用户
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean findByEmail(String email) {
        boolean flag = false;
        Users user = new Users();
        user.setEmail(email);
        if(crud.queryObject(user,"email") != null){
            //存在该邮箱
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean findByTelephone(String telephone) {
        boolean flag = false;
        Users user = new Users();
        user.setTelephone(telephone);
        if(crud.queryObject(user,"telephone") != null){
            //存在该手机号
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean findByIdNumber(String idNumber) {
        boolean flag = false;
        Users user = new Users();
        user.setIdNumber(idNumber);
        if(crud.queryObject(user,"idNumber") != null){
            //存在该手机号
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean saveImg(Users user,List<String> values) {
        return ReflectCRUD.change(user,values,"userName",user);
    }

    @Override
    public Users findByCode(String code) {
        Users user = new Users();
        user.setActiveCode(code);
        return ReflectCRUD.queryObject(user,"activeCode");
    }

    @Override
    public boolean updateStatus(Users user) {
        List<String> setList = new ArrayList<>();
        setList.add("activeStatus");
        Users userAfter = user;
        userAfter.setActiveStatus("Y");
        return ReflectCRUD.change(user,setList,"userName",userAfter);
    }

    @Override
    public Users logIn(Users user,List<String> values) {
        String userPassword = user.getUserPassword();
        //返回一个什么资料都有的对象
        Users u = ReflectCRUD.queryObjectOr(user,values);
        //用不一定正确的密码来替换什么都有的对象的密码，看看到时用户名和密码比对测试是否能成功登录
        u.setUserPassword(userPassword);
        //要搜索的where值
        List<String> values1 = new ArrayList<>();
        values1.add("userName");
        values1.add("userPassword");
        return ReflectCRUD.queryObjectAnd(u,values1);
    }

    @Override
    public boolean updateUserPassword(Users user, List<String> setList) {
        return ReflectCRUD.change(user,setList,"email",user);
    }

    @Override
    public boolean updateUserMessage(Users user, List<String> setList) {
        return ReflectCRUD.change(user,setList,"email",user);
    }

    @Override
    public Manager managerLogIn(Manager manager,List<String> values) {
        return ReflectCRUD.queryObjectAnd(manager,values);
    }

    @Override
    public boolean blockFriend(Users users, List<String> values) {
        return ReflectCRUD.change(users,values,"userId",users);
    }

    @Override
    public List<Reporting> searchReportMessage(Reporting reporting) {
        return ReflectCRUD.query(reporting);
    }

    @Override
    public Users findA(int accuserId) {
        Users user = new Users();
        user.setUserId(accuserId);
        return ReflectCRUD.queryObject(user,"userId");
    }

}
