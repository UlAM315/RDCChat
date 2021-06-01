package com.liangxiaolin.rdcchat.RDCChat1.service.impl;


import com.liangxiaolin.rdcchat.RDCChat1.dao.UsersDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectCRUD;
import com.liangxiaolin.rdcchat.RDCChat1.dao.impl.UsersDaoImpl;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Manager;
import com.liangxiaolin.rdcchat.RDCChat1.entity.ReportMessage;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Reporting;
import com.liangxiaolin.rdcchat.RDCChat1.entity.Users;
import com.liangxiaolin.rdcchat.RDCChat1.service.UsersService;
import com.liangxiaolin.rdcchat.RDCChat1.util.MailUtils;
import com.liangxiaolin.rdcchat.RDCChat1.util.Md5Util;
import com.liangxiaolin.rdcchat.RDCChat1.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

public class UsersServiceImpl implements UsersService {

   UsersDao dao = new UsersDaoImpl();

    @Override
    public boolean regist(Users user) throws Exception {
        user.setIfBlock("N");
        user.setActiveStatus("N");
        //加密密码
        String password = Md5Util.encodeByMd5(user.getUserPassword());
        user.setUserPassword(password);

        //设置激活码，唯一字符串
        user.setActiveCode(UuidUtil.getUuid());

        //3.激活邮件发送，邮件正文？
        //点击这个之后就修改激活码状态
        String content="<a href='http://localhost:8080/RDCChat1/Users/active?code="+user.getActiveCode()+"'>欢迎使用RDCChat，快点击激活8~</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return dao.regist(user);
    }

    @Override
    public boolean findByUserName(String userName) {
        return dao.findByUserName(userName);
    }

    @Override
    public boolean findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public boolean findByTelephone(String telephone) {
        return dao.findByTelephone(telephone);
    }

    @Override
    public boolean findByIdNumber(String idNumber) {
        return dao.findByIdNumber(idNumber);
    }

    @Override
    public boolean saveImg(Users user) {
        List<String> values = new ArrayList<>();
        values.add("avatarImg");
        values.add("backgroundImg");
        return dao.saveImg(user,values);
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        Users user = dao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            return dao.updateStatus(user);
        }else{
            return false;
        }

    }

    @Override
    public Users logIn(Users user) throws Exception {
        String telephone = user.getTelephone();
        String userPassword = user.getUserPassword();
        user.setEmail(telephone);
        user.setIdNumber(telephone);
        //将前端获取到的密码加密 然后存回user中
        user.setUserPassword(Md5Util.encodeByMd5(userPassword));
        List<String> values = new ArrayList<>();
        values.add("telephone");
        values.add("email");
        values.add("idNumber");
        return dao.logIn(user,values);
    }

    @Override
    public boolean updateUserPassword(String email, String userPassword) throws Exception {
        Users user = new Users();
        user.setEmail(email);
        user.setUserPassword(Md5Util.encodeByMd5(userPassword));
        List<String> setList = new ArrayList<>();
        setList.add("userPassword");
        return dao.updateUserPassword(user,setList);
    }

    @Override
    public boolean updateUserMessage(Users user) {
        List<String> setList = new ArrayList<>();
        setList.add("userName");
        setList.add("idNumber");
        setList.add("gender");
        setList.add("telephone");
        return dao.updateUserMessage(user,setList);
    }

    @Override
    public Manager managerLogIn(Manager manager) {
        List<String> values = new ArrayList<>();
        values.add("managerName");
        values.add("managerPassword");
        return dao.managerLogIn(manager,values);
    }

    @Override
    public boolean blockFriend(String userId) {
        int userId0 = Integer.parseInt(userId);
        Users users = new Users();
        users.setUserId(userId0);
        users.setIfBlock("Y");
        List<String> values = new ArrayList<>();
        values.add("ifBlock");
        return dao.blockFriend(users,values);
    }

    @Override
    public List<ReportMessage> searchReportMessage() {
        List<Reporting> reporting = dao.searchReportMessage(new Reporting());
        List<ReportMessage> reportMessageList = new ArrayList<>();
        if (reporting!=null&&!reporting.isEmpty()){
            for (Reporting reporting1 : reporting) {
                int accuserId = reporting1.getAccuserId();
                String accuserName = dao.findA(accuserId).getUserName();
                int accusedId = reporting1.getAccusedId();
                String accusedName = dao.findA(accusedId).getUserName();
                ReportMessage reportMessage = new ReportMessage(accusedId,accuserName,accusedName,reporting1.getMessage());
                reportMessageList.add(reportMessage);
            }
            return reportMessageList;
        }
        return null;
    }
}
