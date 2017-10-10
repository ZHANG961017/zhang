package bwie.com.newsinfo.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bwie.com.newsinfo.R;
import bwie.com.newsinfo.util.HttpData;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by 13435 on 2017/9/13.
 */

public class MyDataAdapter extends BaseAdapter {

    private Context ctx;
    private List<HttpData.ResultBean.DataBean> list = new ArrayList<HttpData.ResultBean.DataBean>();
    private final DisplayImageOptions options;
    private HttpData.ResultBean.DataBean dataBean;

    public MyDataAdapter(Context ctx, List<HttpData.ResultBean.DataBean> list) {
        this.ctx = ctx;
        this.list = list;
        this.options = new DisplayImageOptions.Builder().build();
    }

    //加载更多
    public void loadMore(List<HttpData.ResultBean.DataBean> listMore,boolean flag){
        for (HttpData.ResultBean.DataBean bean: listMore) {
            if(flag){
                list.add(bean);
            }else{
                list.add(0,bean);
            }
        }
        //刷新
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

   /* public String getImageCount(String str){
        if(str !=null){
            return str;
        }else{
            return null;
        }
    }*/
   @Override
    public int getItemViewType(int position) {

       if(position%2==0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        ViewHolder_Horizontal holder_horizontal = null;
        ViewHolder_Vertical holder_vertical = null;
        switch(type){
            case 0:
                if(view == null){
                    holder_horizontal = new ViewHolder_Horizontal();
                    view = View.inflate(ctx, R.layout.item_horizontal, null);
                    holder_horizontal.content_horizontal = (TextView) view.findViewById(R.id.content_horizontal);
                    holder_horizontal.image_horizontal = (PhotoView) view.findViewById(R.id.image_horizontal);
                    view.setTag(holder_horizontal);
                }else{
                    holder_horizontal = (ViewHolder_Horizontal) view.getTag();
                }
                break;
            case 1:
                if(view == null){
                    holder_vertical = new ViewHolder_Vertical();
                    view = View.inflate(ctx, R.layout.item_vertical, null);
                    holder_vertical.content_vertical = (TextView) view.findViewById(R.id.content_vertical);
                    holder_vertical.image_vertical1 = (PhotoView) view.findViewById(R.id.image_vertical1);
                    holder_vertical.image_vertical2 = (PhotoView) view.findViewById(R.id.image_vertical2);
                    holder_vertical.image_vertical3 = (PhotoView) view.findViewById(R.id.image_vertical3);
                    view.setTag(holder_vertical);
                }else{
                    holder_vertical = (ViewHolder_Vertical) view.getTag();
                }
                break;
        }
        dataBean = list.get(i);
        switch(type){
            case 0:
                holder_horizontal.content_horizontal.setText(dataBean.getTitle());
                ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s(),holder_horizontal.image_horizontal,options);
                break;
            case 1:
                holder_vertical.content_vertical.setText(dataBean.getTitle());
                ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s(),holder_vertical.image_vertical1,options);
                ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s02(),holder_vertical.image_vertical2,options);
                ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s03(),holder_vertical.image_vertical3,options);
                break;
        }
        return view;
    }

    class ViewHolder_Vertical{
        TextView content_vertical;
        PhotoView image_vertical1,image_vertical2,image_vertical3;
    }
    class ViewHolder_Horizontal{
        TextView content_horizontal;
        PhotoView image_horizontal;
    }
}
