package com.playmatecat.utils.dataformat;

import com.playmatecat.commons.structure.Pagination;

/**
 * 辅助计算分页偏移
 * @author blackcat
 *
 */
public class UtilsPagination {
    private UtilsPagination(){}
    
    
    public static int getOffset(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Pagination<T> getPage(Pagination<T> page) {
        if(page == null) {
            try {
                page = (Pagination<T>) Pagination.class.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return page;
    }
}
