package com.guyj.copyjddetails.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.guyj.copyjddetails.R;
import com.guyj.copyjddetails.adapter.VPAdapter;
import com.guyj.copyjddetails.entity.TabEntity;
import com.guyj.copyjddetails.widgets.HorViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 整体页面 控制title back_icon的alpha值
 * 沉浸式状态栏控制
 * 所有页面没有加base自己根据实际情况修改
 */
public class MainActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener
		,DetailNextFragment.OnFragmentInteractionListener
		,DetailTotalFragment.OnFragmentInteractionListener
		,TwoFragment.OnFragmentInteractionListener
		,ThreeFragment.OnFragmentInteractionListener
{

	HorViewPager vp;//水平viewpager
	CommonTabLayout tl;//tab layout导航
	TextView tv_title;//图文详情页标题
	RelativeLayout rl_title;//标题栏父布局
	VPAdapter mVPAdapter;
	FragmentManager fm;
	View viewEmptyTop;//空占位view
	ViewGroup.LayoutParams layoutParams;
	List<Fragment> fgList=new ArrayList();//左右切换的3个页面
	ImageView iv_back;//返回按钮，分享或者别的按钮透明度变换可参考此控件

	private String[] mTitles = {"商品", "评价", "详情"};//tab标题
	private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();//tab导航实体集合

	float titleAlphaValue=0f;//标题栏透明度 根据banner滑动 0-1
	float iconAlphaValue=0f;//返回 分享等图标透明度 根据banner滑动 1-0-1 到0换图片
	float scrollAlphaValue=0f;//viewpager左右滑动时 从 titleAlphaValue-0 or 0-titleAlphaValue 的过程
	float iconScrollAlphaValue=0f;
	int rvScrollY=0;//recycleView滑动距离
	float bannerHeight=0;//第一个banner item高度
	int currentDetailPage=0;//当前在详情首页(0) or 图文详情页(1)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		vp=findViewById(R.id.vp);
		tl=findViewById(R.id.tl_1);
		tv_title=findViewById(R.id.tv_title);
		viewEmptyTop=findViewById(R.id.viewEmptyTop);
		iv_back=findViewById(R.id.iv_back);
		rl_title=findViewById(R.id.rl_title);

		/**
		 * 计算占位view高度
		 */
		layoutParams = viewEmptyTop.getLayoutParams();
		layoutParams.height = getStatusBarHeight();
		viewEmptyTop.setLayoutParams(layoutParams);
		/**
		 * 沉浸式
		 */
		ImmersionBar.with(MainActivity.this).statusBarDarkFont(true).init();

		fm=getSupportFragmentManager();
		fgList.add(DetailTotalFragment.newInstance("",""));
		fgList.add(TwoFragment.newInstance("",""));
		fgList.add(ThreeFragment.newInstance("",""));
		mVPAdapter=new VPAdapter(fm,fgList);
		vp.setAdapter(mVPAdapter);
		vp.setOffscreenPageLimit(3);
		vp.setCurrentItem(0);
		for (int i = 0; i < mTitles.length; i++) {
			mTabEntities.add(new TabEntity(mTitles[i]));
		}
		tl.setTabData(mTabEntities);
		tl.setCurrentTab(0);
		tl.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelect(int position) {
				//互相绑定切换
				vp.setCurrentItem(position);
			}

			@Override
			public void onTabReselect(int position) {

			}
		});
		vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (position==0){
					//计算左右滑动时，标题栏与占位view的透明度
					scrollAlphaValue=(1-titleAlphaValue)*positionOffset+titleAlphaValue;
					//保证能隐藏的误差容错
					if (scrollAlphaValue<=0.05f){
						scrollAlphaValue=0;
					}
					viewEmptyTop.setAlpha(scrollAlphaValue);
					rl_title.setAlpha(scrollAlphaValue);
					if (rvScrollY<bannerHeight/2){//当滑动时在banner上半部分
						if (positionOffset<0.5){//滑动位置靠左不到一半时黑底白箭头
							iconScrollAlphaValue=(1-positionOffset*2)*iconAlphaValue;
							iv_back.setImageResource(R.drawable.ic_back_black_bg);
							iv_back.setAlpha(iconScrollAlphaValue);
						}else {//滑动位置靠左超过一半时白底黑箭头
							iconScrollAlphaValue=(positionOffset-0.5f)*2;
							iv_back.setImageResource(R.drawable.ic_back_white_bg);
							iv_back.setAlpha(iconScrollAlphaValue);
						}
					}else {//当滑动时在banner下半部分
						iconScrollAlphaValue=(1-iconAlphaValue)*positionOffset+iconAlphaValue;
						iv_back.setAlpha(iconScrollAlphaValue);
					}

				}
			}

			@Override
			public void onPageSelected(int position) {
				//互相绑定切换
				tl.setCurrentTab(position);
				if (position>0){//非第一页时，标题栏不透明
					rl_title.setAlpha(1);
				}else {//回到第一页时，重新赋值原透明度
					viewEmptyTop.setAlpha(titleAlphaValue);
					rl_title.setAlpha(titleAlphaValue);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	/**
	 * fragment发来的内容
	 * @param uri
	 */
	@Override
	public void onFragmentInteraction(Uri uri) {
		if (TextUtils.equals(uri.getFragment(),"DetailFragment")){
			//得到列表滑动距离和banner高度并转换
			String str=uri.getQueryParameter("rv_scrollY");
			String str2=uri.getQueryParameter("banner_height");
			Log.e("tag",str2);
			rvScrollY=Integer.valueOf(str);
			bannerHeight=Float.valueOf(str2);
			if (rvScrollY<1){//首页列表最上方时
				titleAlphaValue=0f;
				iconAlphaValue=1f;
				rl_title.setAlpha(titleAlphaValue);
				viewEmptyTop.setAlpha(titleAlphaValue);
				iv_back.setAlpha(iconAlphaValue);
			} else if (rvScrollY>0&&rvScrollY<bannerHeight){//滑动距离在第一条banner之间时
				titleAlphaValue=rvScrollY/bannerHeight;
				rl_title.setAlpha(titleAlphaValue);
				viewEmptyTop.setAlpha(titleAlphaValue);
				if (rvScrollY<bannerHeight/2){//当滑动在banner上半部分
					iv_back.setImageResource(R.drawable.ic_back_black_bg);//使用黑底白勾
					iconAlphaValue=1-titleAlphaValue*2;
					if (iconAlphaValue>=0){
						iv_back.setAlpha(iconAlphaValue);
					}
				}else {//当滑动在banner下半部分
					iv_back.setImageResource(R.drawable.ic_back_white_bg);//使用白底黑勾
					iconAlphaValue=(rvScrollY-(bannerHeight*0.5f))/(bannerHeight*0.5f);
					iv_back.setAlpha(iconAlphaValue);
				}
			} else {//滑动距离已超过banner之时
				viewEmptyTop.setBackgroundColor(Color.WHITE);
				titleAlphaValue=1f;
				rl_title.setAlpha(titleAlphaValue);
				viewEmptyTop.setAlpha(titleAlphaValue);
			}
		}
		if (TextUtils.equals(uri.getFragment(),"DetailTotalFragment")){
			//得到当前显示在首页还是图文详情页
			String str=uri.getQueryParameter("which_page");
			currentDetailPage=Integer.valueOf(str);
			if (currentDetailPage==0){//首页
				tv_title.setVisibility(View.GONE);
				tl.setVisibility(View.VISIBLE);
			}else {//图文详情
				tv_title.setVisibility(View.VISIBLE);
				tl.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 计算状态栏高度（包括刘海屏高度）
	 * @param key
	 * @return
	 */
	private int getInternalDimensionSize(String key) {
		int result = 0;
		try {
			int resourceId = getResources().getIdentifier(key, "dimen", "android");
			if (resourceId > 0) {
				result = Math.round(getResources().getDimensionPixelSize(resourceId) * Resources.getSystem().getDisplayMetrics().density / getResources().getDisplayMetrics().density);
			}
		} catch (Resources.NotFoundException ignored) {
			return 0;
		}
		return result;
	}

	/**
	 * 获取状态栏高度
	 * @return
	 */
	public int getStatusBarHeight() {
		return getInternalDimensionSize("status_bar_height");
	}

	/**
	 * dp转px 计算占位view高度时用
	 * @param dpValue
	 * @return
	 */
	public static int dp2px( float dpValue){
		final float scale =  Resources.getSystem().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
