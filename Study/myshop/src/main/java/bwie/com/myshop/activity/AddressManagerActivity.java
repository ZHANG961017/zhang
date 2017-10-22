package bwie.com.myshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.adapter.AddressAdapter;
import bwie.com.myshop.bean.Address_ZhanBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {

    private Button address;
    private ListView lv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);

        address = (Button) findViewById(R.id.address);
        lv_add = (ListView) findViewById(R.id.lv_add);
        address.setOnClickListener(this);

        SharedPreferences shared = BaseActivity.getSharedPreferencesInstance(this);
        String key = shared.getString("key", "");
        Map<String,String> zhanMap=new HashMap<>();
        zhanMap.put("key",key);

        OkHttp3Utils.doPost(OptionUtil.SHOWADDRESS, zhanMap, new GsonObjectCallback<Address_ZhanBean>() {
            @Override
            public void onUi(Address_ZhanBean address_zhanBean) {
                if(address_zhanBean.getCode()==200){
                    List<Address_ZhanBean.DatasBean.AddressListBean> address_list = address_zhanBean.getDatas().getAddress_list();
                    AddressAdapter addressAdapter=new AddressAdapter(AddressManagerActivity.this,address_list);
                    lv_add.setAdapter(addressAdapter);
                }
            }
            @Override
            public void onFailed(Call call, IOException e) {
            }
        });
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(AddressManagerActivity.this,AddAddressActivity.class);
        startActivity(intent);
    }
}
