package com.playmatecat.cas.domains.dto;


/**
 * URI资源Dto
 * @author blackcat
 *
 */
public class UriResourceDto {
    private Long id;
    
    /** 权限编码 **/
    private String uriWildcard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUriWildcard() {
        return uriWildcard;
    }

    public void setUriWildcard(String uriWildcard) {
        this.uriWildcard = uriWildcard;
    }
    
    
}
