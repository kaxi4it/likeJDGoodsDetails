package com.guyj.copyjddetails.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guyj.copyjddetails.R;
import com.guyj.copyjddetails.adapter.RvSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页 上面一页带banner的列表页
 * 所有页面没有加base自己根据实际情况修改
 */
public class DetailFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	private List<String> datas;
	private RecyclerView mRecyclerView;
	private int scrollY;//mRecyclerView的总体滑动距离
	private int banner_height;//mRecyclerView第一条banner的高度

	public DetailFragment() {
	}

	public static DetailFragment newInstance(String param1, String param2) {
		DetailFragment fragment = new DetailFragment();
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
		return inflater.inflate(R.layout.fragment_detail, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRecyclerView=view.findViewById(R.id.rv);
		datas=new ArrayList<>();
		//假数据
		for (int i = 0; i < 10; i++) {
			datas.add("");
		}
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
		mRecyclerView.setAdapter(new RvSimpleAdapter(getActivity(),datas));
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				scrollY+=dy;//计算总滑动距离
				banner_height=recyclerView.getLayoutManager().getChildAt(0).getHeight();//计算第一条banner高度
				if (mListener != null) {//发送回调给activity接收处理，数据封在uri里
					Uri.Builder builder=new Uri.Builder();
					Uri uri=builder.scheme("scheme")
							.fragment("DetailFragment")
							.authority("authority")
							.appendQueryParameter("rv_scrollY",scrollY+"")
							.appendQueryParameter("banner_height",banner_height+"")
							.build();
					mListener.onFragmentInteraction(uri);
				}
			}
		});

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof DetailFragment.OnFragmentInteractionListener) {
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
