package com.guyj.copyjddetails.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guyj.copyjddetails.R;

import java.util.List;

/**
 * Created by guyj on 2018/12/12.
 * 模拟京东商品详情首页的banner和多viewType，自己改写就好了
 */
public class RvSimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<String> mList;
	private Context mContext;
	public RvSimpleAdapter(Context context, List<String> lists) {
		mContext=context;
		mList=lists;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view=LayoutInflater.from(mContext).inflate(R.layout.item_simple_1,parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof MyViewHolder){
			if (position==0){
				((MyViewHolder) holder).tv.setText("假装banner ");
				((MyViewHolder) holder).tv.setBackgroundColor(Color.parseColor("#4D88FF"));
			}else if (position==mList.size()-1){
				((MyViewHolder) holder).tv.setText("上滑查看图文详情");
				((MyViewHolder) holder).tv.setBackgroundColor(Color.parseColor("#4D88FF"));
			}else {
				((MyViewHolder) holder).tv.setText("simple "+position);
				((MyViewHolder) holder).tv.setBackgroundColor(Color.WHITE);
			}

		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
		TextView tv;
		public MyViewHolder(View itemView) {
			super(itemView);
			tv=itemView.findViewById(R.id.tv);
		}
	}

}
