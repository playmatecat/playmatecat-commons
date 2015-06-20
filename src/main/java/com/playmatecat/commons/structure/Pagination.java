package com.playmatecat.commons.structure;

import java.util.List;

/**
 * 通用分页数据格式
 * @author blackcat
 *
 */
public class Pagination<T> {
    
    private final static int DEFAULT_PAGESIZE = 10;
    
    private final static int DEFAULT_PAGENO = 1;
    
    private final static int MAX_PAGESIZE = 50;
    
    private final static int MIN_PAGENO = 1;
    
    private int pageSize = DEFAULT_PAGESIZE;

    private int pageNo = DEFAULT_PAGENO;
    
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
    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        pageSize = pageSize <= MAX_PAGESIZE ? pageSize : MAX_PAGESIZE;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        pageNo = pageNo >= MIN_PAGENO ? pageNo : MIN_PAGENO;
        this.pageNo = pageNo;
    }
    
    /*----general get & set-----*/


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
