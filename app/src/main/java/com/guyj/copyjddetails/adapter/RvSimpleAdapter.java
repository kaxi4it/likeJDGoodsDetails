package com.guyj.copyjddetails.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guyj.copyjddetails.R;
import com.guyj.copyjddetails.widgets.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guyj on 2018/12/12.
 * 模拟京东商品详情首页的banner和多viewType，自己改写就好了
 */
public class RvSimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<String> mList;
	private List<String> images;
	private Context mContext;
	public RvSimpleAdapter(Context context, List<String> lists) {
		this.mContext=context;
		this.mList=lists;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType==0){
			View view=LayoutInflater.from(mContext).inflate(R.layout.item_simple_2,parent,false);
			return new BannerViewHolder(view);
		}else{
			View view=LayoutInflater.from(mContext).inflate(R.layout.item_simple_1,parent,false);
			return new MyViewHolder(view);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position==0){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof BannerViewHolder){
			images=new ArrayList<>();
			images.add("http://d.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb38c2414d0d433c895d1430cb3.jpg");
			images.add("http://b.hiphotos.baidu.com/image/pic/item/11385343fbf2b2119202e609c78065380cd78e4c.jpg");
			images.add("http://h.hiphotos.baidu.com/image/pic/item/dcc451da81cb39dbc1f90411dd160924ab1830bf.jpg");
			((BannerViewHolder) holder).mBanner.setImages(images).setImageLoader(new GlideImageLoader()).isAutoPlay(false).setBannerStyle(BannerConfig.NUM_INDICATOR).start();
		}
		if (holder instanceof MyViewHolder){
			if (position==mList.size()-1){
				((MyViewHolder) holder).tv.setText("上滑查看图文详情");
				((MyViewHolder) holder).tv.setBackgroundColor(Color.parseColor("#4D88FF"));
			}else {
				((MyViewHolder) holder).tv.setText("simple "+position);
				((MyViewHolder) holder).tv.setBackgroundColor(Color.parseColor("#eeeeee"));
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
	class BannerViewHolder extends RecyclerView.ViewHolder{
		Banner mBanner;
		public BannerViewHolder(View itemView) {
			super(itemView);
			mBanner=itemView.findViewById(R.id.banner);
		}
	}

}
