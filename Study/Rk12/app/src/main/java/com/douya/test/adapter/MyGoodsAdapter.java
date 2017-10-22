package com.douya.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.test.R;
import com.douya.test.bean.GoodsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/15.
 */

public class MyGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<GoodsBean.DataBean> list;



    public MyGoodsAdapter(Context context, List<GoodsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view_list = LayoutInflater.from(context).inflate(R.layout.item_list, null);


            return new MyList(view_list);
        }else if(viewType==1){
            View item_grid = LayoutInflater.from(context).inflate(R.layout.item_grid, null);
            
            return new MyGrid(item_grid);
        }
        return null;
    }

    private void getWith(View v) {
        //得到WindowManager
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //得到屏幕的宽
        int width = wm.getDefaultDisplay().getWidth();
        //获取LayoutParams
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //把屏幕的宽给view
        params.width = width;
        v.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsBean.DataBean bean = list.get(position);
        String title = bean.getTitle();
        String images = bean.getImages();
        String[] split = images.split("\\|");
        int type = getItemViewType(position);
        if(type==0){
            MyList myList=new MyList(holder.itemView);
            myList.tv_list.setText(title);
            Picasso.with(context).load(split[0]).into(myList.iv_list);
        }
        if(type==1){
            MyGrid myGrid=new MyGrid(holder.itemView);
            myGrid.tv_grid.setText(title);
            Picasso.with(context).load(split[0]).into(myGrid.iv_grid);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 0;
        }else {
            return 1;
        }

    }

    public class MyList extends RecyclerView.ViewHolder{
        private  ImageView iv_list;
        private  TextView tv_list;
        public MyList(View itemView) {
            super(itemView);
            iv_list = (ImageView) itemView.findViewById(R.id.iv_list);
            tv_list = (TextView) itemView.findViewById(R.id.tv_list);
        }
    }
    public class MyGrid extends RecyclerView.ViewHolder{
        private  ImageView iv_grid;
        private  TextView tv_grid;
        public MyGrid(View itemView) {
            super(itemView);
            iv_grid = (ImageView) itemView.findViewById(R.id.iv_grid);
            tv_grid = (TextView) itemView.findViewById(R.id.tv_grid);
        }
    }
}
