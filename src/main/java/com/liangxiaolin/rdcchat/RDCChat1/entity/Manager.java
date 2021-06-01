package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class Manager {

    private int managerId;
    private String managerName;
    private String managerPassword;

    public Manager() {
    }

    public Manager(int managerId, String managerName, String managerPassword) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerPassword = managerPassword;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", managerName='" + managerName + '\'' +
                ", managerPassword='" + managerPassword + '\'' +
                '}';
    }
}
