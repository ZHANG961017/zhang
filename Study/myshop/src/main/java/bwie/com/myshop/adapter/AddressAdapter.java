package bwie.com.myshop.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.bean.Address_ZhanBean;
import bwie.com.myshop.bean.DelBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class AddressAdapter extends BaseAdapter{

    private Context context;
    private List<Address_ZhanBean.DatasBean.AddressListBean> list;

    public AddressAdapter(Context context, List<Address_ZhanBean.DatasBean.AddressListBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view = View.inflate(context, R.layout.item_address,null);
            holder.zhan_name = view.findViewById(R.id.zhan_name);
            holder.zhan_phone = view.findViewById(R.id.zhan_phone);
            holder.zhan_address = view.findViewById(R.id.zhan_address);
            holder.zhan_del = view.findViewById(R.id.zhan_del);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final Address_ZhanBean.DatasBean.AddressListBean bean = list.get(i);
        holder.zhan_name.setText(bean.getTrue_name());
        holder.zhan_phone.setText(bean.getMob_phone());
        holder.zhan_address.setText(bean.getArea_info());
        holder.zhan_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shared = BaseActivity.getSharedPreferencesInstance(context);
                String key = shared.getString("key", "");
                Map<String,String> delMap = new HashMap<String, String>();
                delMap.put("key",key);
                delMap.put("address_id",bean.getAddress_id());
                OkHttp3Utils.doPost(OptionUtil.ADDRESS_DEL, delMap, new GsonObjectCallback<DelBean>() {
                    @Override
                    public void onUi(DelBean logOutBean) {
                        if(logOutBean.getCode()==200){
                            list.remove(i);
                            notifyDataSetChanged();
                            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailed(Call call, IOException e) {
                    }
                });
            }
        });

        return view;
    }
    class ViewHolder{
        TextView zhan_name;
        TextView zhan_phone;
        TextView zhan_address;
        TextView zhan_del;
    }
}
