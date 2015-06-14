package com.playmatecat.utils.dataformat;

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
}
