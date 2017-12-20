package bwie.com.myshop.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bwie.com.myshop.R;
import bwie.com.myshop.bean.GoodsBean;

/**
 * Created by 13435 on 2017/10/18.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> implements View.OnClickListener {

    private Context ctx;
    private List<GoodsBean.DatasBean.GoodsListBean> list;
    private OnItemClickListener mOnItemClickListener =null;

    public GoodsAdapter(Context ctx, List<GoodsBean.DatasBean.GoodsListBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

/*    @Override
    public int getItemViewType(int position) {

        if (mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;

    }*/

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View view = View.inflate(ctx,R.layout.goods,null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        ViewHolder holder = new ViewHolder(viewHolder.itemView);
        holder.goodsName.setText(list.get(position).getGoods_name());
        holder.goodsPrice.setText(list.get(position).getGoods_price());
        holder.itemView.setTag(position);
        if(list.get(position).getGoods_image().isEmpty()){
            return;
        }else{
            Picasso.with(holder.goodsPrice.getContext())
                    .load(list.get(position).getGoods_image_url())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.goodsIv);
        }
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

        ImageView goodsIv;
        TextView goodsName;
        TextView goodsPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsIv = itemView.findViewById(R.id.goodsIv);
            goodsName = itemView.findViewById(R.id.goodsName);
            goodsPrice = itemView.findViewById(R.id.goodsPrice);
        }
    }
   /* @TargetApi(Build.VERSION_CODES.N)
    public void remove(){
        NfcAdapter.OnTagRemovedListener removedListener = new NfcAdapter.OnTagRemovedListener() {
            @Override
            public void onTagRemoved() {
                remove();
            }
        };
    }*/
}
