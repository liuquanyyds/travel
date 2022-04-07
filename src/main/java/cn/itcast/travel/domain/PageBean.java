package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    /* private int totalCount;//总记录数
     private int totalPage;//总页数
     private int currentPage;//当前页码
     private int pageSize;//每页显示的条数
     private List<T> list;//每页显示的数据集合*/
    private int totalCount;
    private int totalPage;
    private int currentPage;
    private int pagesize;
    private List<T> list;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pagesize=" + pagesize +
                ", list=" + list +
                '}';
    }
}
