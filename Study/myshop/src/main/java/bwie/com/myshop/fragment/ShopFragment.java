package bwie.com.myshop.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.adapter.ExpandableAdapter;
import bwie.com.myshop.bean.ShopCarBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

/**
 * Created by 13435 on 2017/9/28.
 */

public class ShopFragment extends Fragment implements View.OnClickListener {

    ExpandableListView epList;
    private CheckBox selectAll;
    private List<ShopCarBean.DatasBean.CartListBean> group;
    private ExpandableAdapter adapter;
    private TextView priceAll;
    private TextView countAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment, container, false);
        //初始化组件
        epList = view.findViewById(R.id.epList);
        selectAll = view.findViewById(R.id.selectAll);
        priceAll = view.findViewById(R.id.priceAll);
        countAll = view.findViewById(R.id.countAll);
        selectAll.setOnClickListener(this);
        //隐藏箭头
        epList.setGroupIndicator(null);
        final SharedPreferences instance = BaseActivity.getSharedPreferencesInstance(getActivity());
        final String key = instance.getString("key", "");
        Map<String,String> map = new HashMap<String,String>();
        map.put("key",key);
        OkHttp3Utils.doPost(OptionUtil.SHOPCAR, map, new GsonObjectCallback<ShopCarBean>() {

            @Override
            public void onUi(ShopCarBean shopCarBean) {
                if(shopCarBean.getCode() == 200){
                    group = shopCarBean.getDatas().getCart_list();
                    adapter = new ExpandableAdapter(getActivity(), group,selectAll,priceAll,countAll);
                    epList.setAdapter(adapter);
                    int count = epList.getCount();
                    for(int i = 0;i<count;i++){
                        epList.expandGroup(i);
                    }
                    Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailed(Call call, IOException e) {
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<group.size();i++){
            group.get(i).setGroupCheck(selectAll.isChecked());
            for(int j=0;j<group.get(i).getGoods().size();j++){
                group.get(i).getGoods().get(j).setChildCheck(selectAll.isChecked());
            }
        }
        adapter.MoneyAllAndCountAll();
        adapter.notifyDataSetChanged();
    }
}
