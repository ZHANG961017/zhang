package bwie.com.myshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.bean.AddAddressBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText add_name;
    private EditText add_phone;
    private EditText add_dress;
    private EditText add_miao;
    private Button add_yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        add_name = (EditText) findViewById(R.id.add_name);
        add_phone = (EditText) findViewById(R.id.add_phone);
        add_dress = (EditText) findViewById(R.id.add_dress);
        add_miao = (EditText) findViewById(R.id.add_miao);
        add_yes = (Button) findViewById(R.id.add_yes);

        add_yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        SharedPreferences shared = BaseActivity.getSharedPreferencesInstance(this);
        String key = shared.getString("key", "");
        Map<String,String> addMap = new HashMap<String, String>();
        addMap.put("key",key);
        addMap.put("true_name",add_name.getText().toString());
        addMap.put("mob_phone",add_phone.getText().toString());
        addMap.put("city_id","1");
        addMap.put("area_id","16");
        addMap.put("address",add_dress.getText().toString());
        addMap.put("area_info",add_miao.getText().toString());
        OkHttp3Utils.doPost(OptionUtil.ADD_ADDRESS, addMap, new GsonObjectCallback<AddAddressBean>() {
            @Override
            public void onUi(AddAddressBean add_address) {
                if(add_address.getCode()==200){
                    Intent intent=new Intent(AddAddressActivity.this,AddressManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    AddAddressActivity.this.finish();
                    Toast.makeText(AddAddressActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

}
