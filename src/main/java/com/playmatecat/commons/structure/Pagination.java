package com.playmatecat.commons.structure;

import java.util.List;

/**
 * 通用分页数据格式
 * @author blackcat
 *
 */
public class Pagination<T> {
    
    private int pageSize;

    private int pageNo;
    
    private List<T> list;
    
    /** 数据总数 **/
    private int total;
    
    /** 总页数 **/
    @SuppressWarnings("unused")
    private int pageCount;
    
    public Pagination(){}
    
    public Pagination(int pageNo,int PageSize) {
        this.pageNo = pageNo;
        this.pageSize = PageSize;
    }
    
    /**
     * 获得总页数
     * @return
     */
    public int getPageCount() {
        return ((total + pageSize -1 )/ pageSize);
    }
    
    /*----get & set-----*/

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    
    
}
