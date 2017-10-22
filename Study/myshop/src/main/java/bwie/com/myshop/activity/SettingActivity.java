package bwie.com.myshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.bean.LogOutBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class SettingActivity extends BaseActivity  {

    private Button logOut;
    private SharedPreferences instance;
    private String key;
    private String username;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //初始化组件
        //logOut = (Button) findViewById(R.id.logOut);
        instance = BaseActivity.getSharedPreferencesInstance(this);
        key = instance.getString("key", "");
        username = instance.getString("name", "");
        flag = instance.getBoolean("flag", false);
            //设置点击监听
            //logOut.setOnClickListener(this);
    }
        public void logOut(View view){
            Map<String,String> map = new HashMap<String,String>();
            map.put("key",key);
            map.put("client", OptionUtil.CLIENT);
            map.put("username",username);
            OkHttp3Utils.doPost(OptionUtil.LOGOUT, map, new GsonObjectCallback<LogOutBean>() {
                @Override
                public void onUi(LogOutBean logOutBean) {
                    if(logOutBean.getDatas() == 1 && logOutBean.getCode() == 200){
                        Intent intent = new Intent(SettingActivity.this,Login_register_Activity.class);
                        startActivity(intent);
                        SharedPreferences.Editor edit = instance.edit();
                        edit.putBoolean("flag",false);
                        edit.commit();
                        Toast.makeText(SettingActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SettingActivity.this, logOutBean.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailed(Call call, IOException e) {
                }
            });
        }
}
