package com.playmatecat.cas.domains.dto;

public class UserLevelDto {
    /**用户id**/
    private Long userId;
    
    /**等级字典id**/
    private Long levelDictId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLevelDictId() {
        return levelDictId;
    }

    public void setLevelDictId(Long levelDictId) {
        this.levelDictId = levelDictId;
    }
    
}
