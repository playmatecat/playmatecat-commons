package com.playmatecat.cas.domains.dto;

/**
 * 角色dto
 * @author blackcat
 *
 */
public class RoleDto {
    private Long id;
    
    /** 角色编码  **/
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
