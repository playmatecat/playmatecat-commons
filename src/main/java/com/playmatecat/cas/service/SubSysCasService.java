package com.playmatecat.cas.service;

import org.springframework.stereotype.Service;

/**
 * 子系统服务类,除了controller之外,子系统鉴权授权也会调用它
 * @author Isa
 *
 */
@Service(value="subSysCasService")
public class SubSysCasService {
	public void say() {
		System.out.println("子系统鉴权限！！！");
	}
}
