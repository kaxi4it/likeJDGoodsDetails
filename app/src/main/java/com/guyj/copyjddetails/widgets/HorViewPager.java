package com.guyj.copyjddetails.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by guyj on 2018/12/13.
 * 描述：水平viewpager
 */
public class HorViewPager extends ViewPager {

	public HorViewPager(Context context) {
		super(context);
	}

	public HorViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}

}
