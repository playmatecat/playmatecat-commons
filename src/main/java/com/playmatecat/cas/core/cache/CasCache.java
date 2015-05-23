package com.playmatecat.cas.core.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

public class CasCache implements Cache<Object, Object> , Serializable{

    private static final long serialVersionUID = 1L;

	@Override
    public void clear() throws CacheException {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public Object get(Object arg0) throws CacheException {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Set<Object> keys() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Object put(Object arg0, Object arg1) throws CacheException {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Object remove(Object arg0) throws CacheException {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public int size() {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    public Collection<Object> values() {
	    // TODO Auto-generated method stub
	    return null;
    }

}
