package com.guyj.copyjddetails.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by guyj on 2018/12/12.
 * 描述:viewpager的适配器
 */
public class VPAdapter extends FragmentPagerAdapter {
	private FragmentManager fragmetnmanager;  //创建FragmentManager
	private List<Fragment> listfragment; //创建一个List<Fragment>
	//定义构造带两个参数
	public VPAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.fragmetnmanager=fm;
		this.listfragment=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return listfragment.get(arg0); //返回第几个fragment
	}

	@Override
	public int getCount() {
		return listfragment.size(); //总共有多少个fragment
	}
}
