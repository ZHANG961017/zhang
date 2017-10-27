package com.bwie.text.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.text.R;
import com.bwie.text.bean.DataBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 13435 on 2017/10/26.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private Context ctx;
    private List<DataBean.SongListBean> list;
    private final DisplayImageOptions options;
    private OnItemClickListener mOnItemClickListener =null;

    public MyAdapter(Context ctx, List<DataBean.SongListBean> list) {
        this.ctx = ctx;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(ctx, R.layout.item_rlv, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder holder1 = new ViewHolder(holder.itemView);
        holder1.title.setText(list.get(position).getTitle());
        holder1.introduce.setText(list.get(position).getAuthor()+"-"+list.get(position).getAlbum_title());
        holder1.itemView.setTag(position);
        ImageLoader.getInstance().displayImage(list.get(position).getPic_small(),holder1.image,options);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //点击事件
    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,introduce;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            introduce = itemView.findViewById(R.id.introduce);
        }
    }
}
