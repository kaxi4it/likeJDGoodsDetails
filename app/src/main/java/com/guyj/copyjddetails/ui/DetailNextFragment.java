package com.guyj.copyjddetails.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guyj.copyjddetails.R;


/**
 * 商品详情页 首页上滑显示的第二页 图文详情
 * 所有页面没有加base自己根据实际情况修改
 */
public class DetailNextFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	public DetailNextFragment() {
	}

	TextView tv_tips;
	ViewGroup.LayoutParams layoutParams;

	public static DetailNextFragment newInstance(String param1, String param2) {
		DetailNextFragment fragment = new DetailNextFragment();
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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_detail_next, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		/**
		 * 这里为了不引起沉浸式状态栏切换时的微闪烁问题，固定用一个占位view
		 */
		tv_tips=view.findViewById(R.id.tv_tips);
		layoutParams = tv_tips.getLayoutParams();
		//状态栏高度+图文详情页标题高度，把实际内容顶在下面正常显示
		layoutParams.height = ((MainActivity)getActivity()).getStatusBarHeight()+((MainActivity)getActivity()).dp2px(44);
		tv_tips.setLayoutParams(layoutParams);
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
