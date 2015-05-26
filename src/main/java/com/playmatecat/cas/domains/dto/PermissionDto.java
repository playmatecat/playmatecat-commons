package com.playmatecat.cas.domains.dto;

/**
 * 权限Dto
 * @author blackcat
 *
 */
public class PermissionDto {
    private Long id;
    
    /** 权限编码 **/
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
}
