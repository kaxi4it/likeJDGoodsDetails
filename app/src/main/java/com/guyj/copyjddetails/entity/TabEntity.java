package com.guyj.copyjddetails.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by guyj on 2018/12/14.
 * 描述: 首页tab导航的实体类
 */
public class TabEntity implements CustomTabEntity {

	private String mTabTitle;

	public TabEntity(String tabTitle) {
		mTabTitle = tabTitle;
	}

	@Override
	public String getTabTitle() {
		return mTabTitle;
	}

	@Override
	public int getTabSelectedIcon() {
		return 0;
	}

	@Override
	public int getTabUnselectedIcon() {
		return 0;
	}
}
