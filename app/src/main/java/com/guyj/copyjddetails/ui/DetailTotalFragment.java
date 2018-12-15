package com.guyj.copyjddetails.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guyj.copyjddetails.R;
import com.guyj.copyjddetails.adapter.VPAdapter;
import com.guyj.copyjddetails.widgets.VerViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品详情第一页 带一个垂直viewpager 可上下滑动切换商品与图文详情
 * 所有页面没有加base自己根据实际情况修改
 */
public class DetailTotalFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	public DetailTotalFragment() {
	}


	VerViewPager vp;//垂直viewpager
	VPAdapter mVPAdapter;
	FragmentManager fm;//内层fm要用child fm
	List<Fragment> fgList=new ArrayList();//上下切换
	int which_page=0;//当前垂直viewpager的选中页

	public static DetailTotalFragment newInstance(String param1, String param2) {
		DetailTotalFragment fragment = new DetailTotalFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_detail_total, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		vp=view.findViewById(R.id.ver_vp);
		fm=getChildFragmentManager();
		/**
		 * 添加商品详情页 第一页与第二页 装到垂直viewpager中
		 */
		fgList.add(DetailFragment.newInstance("",""));
		fgList.add(DetailNextFragment.newInstance("",""));
		mVPAdapter=new VPAdapter(fm,fgList);
		vp.setAdapter(mVPAdapter);
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				//发送当前页面给activity处理title
				which_page=position;
				if (mListener != null) {
					Uri.Builder builder=new Uri.Builder();
					Uri uri=builder.scheme("scheme")
							.fragment("DetailTotalFragment")
							.authority("authority")
							.appendQueryParameter("which_page",which_page+"")
							.build();
					mListener.onFragmentInteraction(uri);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnFragmentInteractionListener {
		void onFragmentInteraction(Uri uri);
	}
}
