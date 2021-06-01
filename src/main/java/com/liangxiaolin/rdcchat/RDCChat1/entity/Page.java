package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.util.List;

public class Page<T> {

    private int totalCount; // 总记录数
    private int totalPage ; // 总页码
    private List<T> list ; // 每页的数据
    private int currentPage ; //当前页码

    public Page() {
    }

    public Page(int totalCount, int totalPage, List<T> list, int currentPage) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.list = list;
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                '}';
    }
}
